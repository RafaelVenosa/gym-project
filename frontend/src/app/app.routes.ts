import { Routes } from '@angular/router';
import { AdminCustomerRegistrationLayoutComponent } from './components/admin/admin-customer-registration-layout/admin-customer-registration-layout.component';
import { AdminCustomerActivationLayoutComponent } from './components/admin/admin-customer-activation-layout/admin-customer-activation-layout.component';
import { TrainerTrainCreationComponent } from './components/trainer/trainer-train-creation/trainer-train-creation.component';
import { TrainerCustomerControlComponent } from './components/trainer/trainer-customer-control/trainer-customer-control.component';
import { TrainerTrainControlComponent } from './components/trainer/trainer-train-control/trainer-train-control.component';
import { CustomerTrainsComponent } from './components/customer/customer-trains/customer-trains.component';
import { TrainerExpiringTrainsComponent } from './components/trainer/trainer-expiring-trains/trainer-expiring-trains.component';
import { AdminPasswordChangeComponent } from './components/admin/admin-password-change/admin-password-change.component';
import { TrainerPasswordChangeComponent } from './components/trainer/trainer-password-change/trainer-password-change.component';
import { CustomerPasswordChangeComponent } from './components/customer/customer-password-change/customer-password-change.component';
import { AdminTrainerRegistrationLayoutComponent } from './components/admin/admin-trainer-registration-layout/admin-trainer-registration-layout.component';
import { AdminRegistrationLayoutComponent } from './components/admin/admin-registration-layout/admin-registration-layout.component';
import { TrainerExpiredTrainsComponent } from './components/trainer/trainer-expired-trains/trainer-expired-trains.component';
import { DefaultLoginLayoutComponent } from './components/default-login-layout/default-login-layout.component';

export const routes: Routes = [
    {
        path:"login",
        component: DefaultLoginLayoutComponent
    },
    {
        path:"",
        component: DefaultLoginLayoutComponent
    },
    {
        path:"admin/customer-registration",
        component: AdminCustomerRegistrationLayoutComponent
    },
    {
        path:"admin/admin-registration",
        component: AdminRegistrationLayoutComponent
    },
    {
        path:"admin/trainer-registration",
        component: AdminTrainerRegistrationLayoutComponent
    },
    {
        path:"admin/customer-activation",
        component: AdminCustomerActivationLayoutComponent
    },
    {
        path:"admin/password-change",
        component: AdminPasswordChangeComponent
    },
    {
        path:"trainer/train-creation",
        component: TrainerTrainCreationComponent
    },
    {
        path:"trainer/customer-control",
        component: TrainerCustomerControlComponent
    },
    {
        path:"trainer/train-control",
        component: TrainerTrainControlComponent
    },
    {
        path:"trainer/expiring-train",
        component: TrainerExpiringTrainsComponent
    },
    {
        path:"trainer/expired-train",
        component: TrainerExpiredTrainsComponent
    },
    {
        path:"trainer/password-change",
        component: TrainerPasswordChangeComponent
    },
    {
        path:"customer/train",
        component: CustomerTrainsComponent
    },
    {
        path:"customer/password-change",
        component: CustomerPasswordChangeComponent
    }
    
];
