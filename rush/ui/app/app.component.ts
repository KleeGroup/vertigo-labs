import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
  <div class="layout-menu-left">
    <nav>
      <ul>
        <li routerLink="/test" routerLinkActive="active">Rush</li>
        <li routerLink="/employees" routerLinkActive="active" >
          <md-icon>person</md-icon>
        </li>
      </ul>
    </nav>
  </div>
  <div class="layout-content">
    <router-outlet></router-outlet>
  </div>
`
})
export class AppComponent {
}

