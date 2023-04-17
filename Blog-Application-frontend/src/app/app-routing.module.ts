import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Module/blog-home/home/home.component';
import { SignupComponent } from './Module/blog-user/signup/signup.component';
import { EmailVerifyComponent } from './Module/blog-user/email-verify/email-verify.component';

const routes: Routes = [
  {path:"signup", component:SignupComponent},
  {path:"",component:HomeComponent},
  {path:"home",component:HomeComponent},
  {path:"email-verify",component:EmailVerifyComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
