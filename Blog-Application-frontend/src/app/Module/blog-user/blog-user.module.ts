import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxSpinnerModule } from 'ngx-spinner';
import { EmailVerifyComponent } from './email-verify/email-verify.component';




@NgModule({
  declarations: [
  
    SignupComponent,
       EmailVerifyComponent,
   
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxSpinnerModule,
  ],
  exports:[
    SignupComponent,
    
  
  ]
})
export class BlogUserModule { }
