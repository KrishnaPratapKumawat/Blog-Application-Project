import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = "https://localhost:9091/user/adduser";


  constructor(private http: HttpClient) { }

  public addUser(user:any){
    return this.http.post(`${this.baseUrl}`,user)
  }
}
