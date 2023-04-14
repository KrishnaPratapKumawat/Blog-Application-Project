import { Component } from '@angular/core';
import { User } from '../../class/user';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signUp: FormGroup;
  constructor(private userService: UserService,private router: Router,private fb: FormBuilder) {}
fieldTextType: boolean;
fieldTextTypenew: boolean;



toggleFieldTextType() {
  this.fieldTextType = !this.fieldTextType;
}
toggleFieldTextTypeRepeat() {
  this.fieldTextTypenew = !this.fieldTextTypenew;
}
public user = {
  userName: '',
    email: '',
    password:'',
    about: '',
}
onSubmit(): void{
  alert("submit");
  console.log(this.user);
}

ngOnInit() {
 
}

  // public user: User = new User();


}
