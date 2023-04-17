import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, AbstractControl } from "@angular/forms";
import { UserService } from '../../service/user.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';




@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent { 
  typeSelected: string;
  constructor(private spinnerService: NgxSpinnerService,private userService: UserService,private router: Router) {

  }
  repeatPassword: string = 'none';
fieldTextType: boolean;
toggleFieldTextType() {
  this.fieldTextType = !this.fieldTextType;
}
ngOnInit(): void {
  this.spinnerService.show();

  setTimeout(() => {
    this.spinnerService.hide();
  }, 5000); // 5 seconds
}

signup = new FormGroup({

  userName: new FormControl('',[
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(25)
  ]),
  email: new FormControl('',[
    Validators.required, Validators.email
  ]
  ),
  password: new FormControl('',
  [
    Validators.required,
    Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-zd$@$!%*?&].{8,15}'),
    Validators.minLength(8),
    Validators.maxLength(15)
  ]),
  about: new FormControl('',[
    Validators.required,
    Validators.maxLength(255)
  ]),
  acceptTerms: new FormControl(false,[Validators.requiredTrue]),
});

onSubmit(): void{
  
  
    if (this.signup.invalid) {
      return;
    }

    console.log(this.signup.value);
    this.userService.addUser(this.signup.value).subscribe(
      
      (res) => {
        console.log(res);
        setTimeout(() => {
          Swal.fire('success','user added successfully','success').then(() => {
            this.router.navigate(['email-verify']);
          })
        }, 1000)
      },
      (err) => {
        console.log(err);
        setTimeout(() => {
             Swal.fire('error','user added Unsuccessfully','error')
        }, 5000)
      }
    )
}

get userName():FormControl{
  return this.signup.get('userName')  as FormControl;
}
get email():FormControl{
  return this.signup.get('email')  as FormControl;
}
get password():FormControl{
  return this.signup.get('password') as FormControl;
}

get about():FormControl{
  return this.signup.get('about') as FormControl;
}
get acceptTerms():FormControl{
  return this.signup.get('acceptTerms') as FormControl;
}

  

  
}
