import { Component, OnInit, HostBinding } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  moduleId: module.id,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  selectedScope: string = "/employees";

  constructor(
    private router: Router
  ) {
  }

  search(term: string) {
    this.router.navigate([this.selectedScope], { queryParams: { 'q': term } })

  }


}
