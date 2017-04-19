
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';
import { URLSearchParams, QueryEncoder } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
// Observable class extensions
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/switchMap';
// Observable operators
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/combineLatest';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/skip';

import * as _ from "lodash";

import { EmployeeService } from 'app/generated/services/employees.service';
import { Employee } from 'app/generated/model/employee';
import { FacetedQueryResult, Criteria, ListState, Facets } from 'app/shared/criteria';

@Component({
  moduleId: module.id,
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  private selectedEmployee: Employee;
  private employees = new BehaviorSubject<FacetedQueryResult<Employee>>(new FacetedQueryResult<Employee>());
  private criteria = new BehaviorSubject<Criteria>(new Criteria());
  private listState = new BehaviorSubject<ListState>(new ListState());
  private facets = new BehaviorSubject<Facets>(new Facets());

  private typing: boolean;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {

    this.route.queryParams.subscribe(queryParams => {
      if (!this.typing) {
        this.criteria.next(Criteria.fromQueryParams(queryParams));
        this.facets.next(Facets.fromQueryParams(queryParams));
      }
      this.typing = false;

    }
    )

    this.listState
      .skip(1) // skip first to avoid duplicate call at beginning
      .distinctUntilChanged((a, b) => _.isEqual(a, b))
      .subscribe(listState =>
        this.employeeService.searchEmployees(this.criteria.getValue(), this.facets.getValue(), listState).subscribe(facetedQueryResult => {
          facetedQueryResult.list = this.employees.getValue().list.concat(facetedQueryResult.list)
          this.employees.next(facetedQueryResult);
        })
      );

    var realChange = this.criteria
      .map(event => {
        this.typing = true;
        return event;
      })
      .debounceTime(500)        // wait 500ms after each keystroke before considering the term
      .distinctUntilChanged((a, b) => a.term === b.term)
    //.filter(criteria => criteria.term != ""); // do we keep it?


    realChange.combineLatest(
      this.facets.distinctUntilChanged((a, b) => _.isEqual(a, b)))
      .subscribe(tuple => {
        this.listState.getValue().skipRows = 0;
        this.employeeService.searchEmployees(this.criteria.getValue(), this.facets.getValue(), this.listState.getValue()).subscribe(facetedQueryResult => {
          this.employees.next(facetedQueryResult);
        })
        this.router.navigate([this.route.routeConfig.path], { queryParams: { ...tuple[0].toQueryParams(), ...tuple[1].toQueryParams() } })
      })


  }


  // Push a search term into the observable stream.
  search(term: string): void {
    this.selectedEmployee = null;
    var newCriteria = new Criteria();
    newCriteria.term = term;
    this.criteria.next(newCriteria);
  }


  onSelect(employee: Employee) {
    this.selectedEmployee = employee;
  }

  addFacet(facetCode: string, facetValue: string) {
    var facets = _.clone(this.facets.getValue());
    facets[facetCode] = facetValue;
    this.facets.next(facets);

  }

  removeFacet(facetCode: string) {
    var facets = _.clone(this.facets.getValue());
    delete facets[facetCode];
    this.facets.next(facets);

  }

  isFacetSelected(facetCode: string): boolean {
    return this.facets.getValue().hasValue(facetCode);
  }

  loadMore() {
    var listState = _.clone(this.listState.getValue());
    listState.skipRows += listState.maxRows;
    this.listState.next(listState);

  }

  initialCriteria(): string {
    return this.criteria.getValue().term;
  }

}
