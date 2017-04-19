import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';

import { Employee } from 'app/generated/model/employee';
import { FacetedQueryResult, Facet, FacetValue, Criteria, ListState, Facets } from 'app/shared/criteria';

let EMPLOYEES = [
  new Employee(1, 'Matthieu', "http://directory.lan.net/people/photo/49ba40552126e644ab18855db810c911"),
  new Employee(2, 'Philippe', "http://directory.lan.net/people/photo/57e9257889df66428da3a8a3106751f0"),
  new Employee(3, 'François', "http://directory.lan.net/people/photo/2f1d1995b431c14188b540aff9023f3f"),
  new Employee(4, 'Xavier', "http://directory.lan.net/people/photo/dcaec56363902940a8b10b7f48807f80"),
  new Employee(5, 'Nicolas', "http://directory.lan.net/people/photo/26dbbc9dcef0c34aa742441574dc82e4"),
  new Employee(6, 'Franckie', "http://directory.lan.net/people/photo/0c8e54e5c56a1a44ad3ce8c8fa20327f")
];

let employeePromise = Promise.resolve(EMPLOYEES);

@Injectable()
export class EmployeeService {
  getEmployees() { return employeePromise; }

  searchEmployees(searchCriteria: Criteria, facets: Facets, listState: ListState): Observable<FacetedQueryResult<Employee>> {
    console.log("searchEmployees", searchCriteria, facets, listState);
    if (searchCriteria.term) {
      var filtered = EMPLOYEES
        .filter(employee => employee.name.toLowerCase().startsWith(searchCriteria.term.toLowerCase()));
      return Observable.of(new FacetedQueryResult(filtered, [new Facet("TITI", [new FacetValue("toto", 2), new FacetValue("tata", 3)])], filtered.length))
    } else {
      return Observable.of(new FacetedQueryResult(EMPLOYEES, [new Facet("TITI", [new FacetValue("toto", 2), new FacetValue("tata", 3)])], EMPLOYEES.length));
    }
  }

  getEmployee(id: number | string) {
    return employeePromise
      .then(employees => employees.find(employee => employee.emp_id === +id));
  }
}