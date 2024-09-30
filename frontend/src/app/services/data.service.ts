import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) {
  }

  private authuserBaseUrl = 'http://localhost:8080/gym-authuser/user';

  private exercisesBaseUrl = 'http://localhost:8080/gym-exercises/train';

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  registerCustomer(data: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const customerRegistrationUrl = `${this.authuserBaseUrl}/signup`;

    return this.http.post(customerRegistrationUrl, data, options);
  }

  registerAdmin(data: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const adminRegistrationUrl = `${this.authuserBaseUrl}/register/admin`;

    return this.http.post(adminRegistrationUrl, data, options);
  }

  registerInstructor(data: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const instructorRegistrationUrl = `${this.authuserBaseUrl}/register/instructor`;

    return this.http.post(instructorRegistrationUrl, data, options);
  }

  getAuthUser(params: HttpParams): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers,
      params: params
    };
    const searchUserForActivationUrl = `${this.authuserBaseUrl}`;

    return this.http.get(searchUserForActivationUrl, options);
  }

  activateUserYear(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const activateCustomerYearUrl = `${this.authuserBaseUrl}/activate/year/${id}`;

    return this.http.put(activateCustomerYearUrl, {}, options);
  }

  activateUserMounth(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const activateCustomerMounthUrl = `${this.authuserBaseUrl}/activate/mounth/${id}`;

    return this.http.put(activateCustomerMounthUrl, {}, options);
  }

  deactivateUser(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deactivateUserUrl = `${this.authuserBaseUrl}/deactivate/${id}`;

    return this.http.put(deactivateUserUrl, {}, options);
  }

  deleteCustomer(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deleteCustomerUrl = `${this.authuserBaseUrl}/deletecustomer/${id}`;

    return this.http.delete(deleteCustomerUrl, options);
  }

  deleteTrainer(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deleteTrainerUrl = `${this.authuserBaseUrl}/deletetrainer/${id}`;

    return this.http.delete(deleteTrainerUrl, options);
  }

  deleteAdmin(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deleteAdminUrl = `${this.authuserBaseUrl}/deleteadmin/${id}`;

    return this.http.delete(deleteAdminUrl, options);
  }

  updatePassword(data: any): Observable<any> {
    const id = localStorage.getItem('userId');
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const updatePasswordUrl = `${this.authuserBaseUrl}/password/${id}`;

    return this.http.put(updatePasswordUrl, data, options);
  }

  createTrain(data: any): Observable<any> {
    const id = localStorage.getItem('userId');
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const createTrainUrl = `${this.exercisesBaseUrl}/create/${id}`;

    return this.http.post(createTrainUrl, data, options);
  }

  registerUserInTrain(id: string, body: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const registerUserInTrainUrl = `${this.exercisesBaseUrl}/register/user/${id}`;

    return this.http.post(registerUserInTrainUrl, body, options);
  }

  getAllTrainsFromOneTrainer(): Observable<any> {
    const id = localStorage.getItem('userId');
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getAllTrainsFromOneTrainerUrl = `${this.exercisesBaseUrl}/trainer/${id}`;

    return this.http.get(getAllTrainsFromOneTrainerUrl, options)
  }

  getTrainsExpiringIn10Days(): Observable<any> {
    const id = localStorage.getItem('userId');
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getTrainsExpiringIn10DaysUrl = `${this.exercisesBaseUrl}/${id}/expiring-in-10-days`;

    return this.http.get(getTrainsExpiringIn10DaysUrl, options)
  }

  getExpiredTrains(): Observable<any> {
    const id = localStorage.getItem('userId');
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getExpiredTrainsUrl = `${this.exercisesBaseUrl}/${id}/expired`;

    return this.http.get(getExpiredTrainsUrl, options)
  }

  searchUsersExerciseMicroservice(params: HttpParams): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = {
      headers: headers,
      params: params
    };

    return this.http.get(this.exercisesBaseUrl, options);
  }

  getOneTrain(trainId: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getOneTrainUrl = `${this.exercisesBaseUrl}/${trainId}`

    return this.http.get(getOneTrainUrl, options)
  }

  getAllTrainsFromOneUser(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getAllTrainsFromOneUserUrl = `${this.exercisesBaseUrl}/user/${id}`;

    return this.http.get(getAllTrainsFromOneUserUrl, options)
  }

  getAllUsersFromOneTrain(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const getAllUsersFromOneTrainUrl = `${this.exercisesBaseUrl}/users-affiliated/${id}`;

    return this.http.get(getAllUsersFromOneTrainUrl, options)
  }

  deleteTrain(trainId: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deleteTrainUrl = `${this.exercisesBaseUrl}/delete/${trainId}`;

    return this.http.delete(deleteTrainUrl, options)
  }

  updateTrain(trainId: string, body: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const updateTrainUrl = `${this.exercisesBaseUrl}/update/${trainId}`;

    return this.http.put(updateTrainUrl, body, options)
  }

  extendTrain(trainId: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const extendTrainUrl = `${this.exercisesBaseUrl}/extend/${trainId}`;

    return this.http.put(extendTrainUrl, {}, options)
  }

  deactivateTrain(trainId: string): Observable<any> {
    const headers = this.getAuthHeaders();
    const options = { 
      headers: headers
    };
    const deactivateTrainUrl = `${this.exercisesBaseUrl}/deactivate/${trainId}`;

    return this.http.put(deactivateTrainUrl, {}, options)
  }
}

