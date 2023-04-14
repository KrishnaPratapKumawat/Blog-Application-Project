import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'module-blog-home-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  ngOnInit(): void {
      
  }
  loggedIn = false;

  // Your login logic here
  login() {
    // Set loggedIn to true when user successfully logs in
    this.loggedIn = true;
  }

}
