import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChartAllModule } from '@syncfusion/ej2-angular-charts';
import { CategoryService, LineSeriesService} from '@syncfusion/ej2-angular-charts';
import { ListViewModule } from '@syncfusion/ej2-angular-lists';
import { ButtonModule  } from '@syncfusion/ej2-angular-buttons';

import { MatIconModule } from '@angular/material/icon';

import { 
	IgxAvatarModule,
	IgxFilterModule,
	IgxIconModule,
	IgxListModule,
    IgxInputGroupModule,
    IgxAutocompleteModule,
	IgxDropDownModule,
    IgxButtonGroupModule,
    IgxRippleModule,
	IgxForOfModule
 } from "igniteui-angular";

// used to create fake backend
import { fakeBackendProvider } from './helpers';

import { appRoutingModule } from './app.routing';
import { JwtInterceptor, ErrorInterceptor } from './helpers';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home';
import { LoginComponent } from './pages/login';
import { RegisterComponent } from './pages/register';
import { DashboardComponent } from './pages/dashboard';
import { AlertComponent, LinechartComponent } from './components';

@NgModule({
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        appRoutingModule,
        BrowserAnimationsModule, 
        ChartAllModule,
        ListViewModule,
        ButtonModule,
        MatIconModule,
        IgxAvatarModule,
		IgxFilterModule,
		IgxIconModule,
		IgxListModule,
		IgxInputGroupModule,
        IgxButtonGroupModule,
        IgxAutocompleteModule,
        IgxDropDownModule,
        IgxRippleModule,
        IgxForOfModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        DashboardComponent,
        LoginComponent,
        RegisterComponent,
        AlertComponent,
        LinechartComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        CategoryService, LineSeriesService,

        // provider used to create fake backend
        //fakeBackendProvider
    ],
    bootstrap: [AppComponent]
})
export class AppModule { };