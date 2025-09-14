# Cheat Sheets Angular Complètes

## Table des Matières

1. [Bases d'Angular](#1-bases-dangular)
2. [TypeScript pour Angular](#2-typescript-pour-angular)
3. [Architecture et Structure](#3-architecture-et-structure)
4. [Services et Dependency Injection](#4-services-et-dependency-injection)
5. [Routing et Navigation](#5-routing-et-navigation)
6. [Forms Angular](#6-forms-angular)
7. [HTTP Client et APIs](#7-http-client-et-apis)
8. [RxJS et Observables](#8-rxjs-et-observables)
9. [Lifecycle Hooks](#9-lifecycle-hooks)
10. [Pipes](#10-pipes)
11. [Directives](#11-directives)
12. [Testing Angular](#12-testing-angular)
13. [Performance et Optimisation](#13-performance-et-optimisation)
14. [Angular CLI](#14-angular-cli)
15. [Guards et Interceptors](#15-guards-et-interceptors)
16. [Modules et Lazy Loading](#16-modules-et-lazy-loading)

---

## 1. Bases d'Angular

### Qu'est-ce qu'Angular ?

Angular est un framework TypeScript open-source développé par Google pour construire des applications web dynamiques et des SPAs (Single Page Applications).

### Structure d'un Composant

```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-example',
  templateUrl: './example.component.html',
  styleUrls: ['./example.component.css']
})
export class ExampleComponent implements OnInit {
  // Propriétés
  title = 'Mon Application';
  users = ['Alice', 'Bob', 'Charlie'];
  isVisible = true;

  constructor() { }

  ngOnInit(): void {
    // Logique d'initialisation
  }

  // Méthodes
  onClick() {
    this.isVisible = !this.isVisible;
  }
}
```

### Template Syntax (Data Binding)

#### Interpolation
```html
<h1>{{ title }}</h1>
<p>Utilisateurs: {{ users.length }}</p>
```

#### Property Binding
```html
<img [src]="imageUrl" [alt]="imageAlt">
<button [disabled]="isDisabled">Cliquez</button>
<div [ngClass]="cssClasses" [ngStyle]="styles">Contenu</div>
```

#### Event Binding
```html
<button (click)="onClick()">Cliquer</button>
<input (keyup)="onKeyUp($event)" (blur)="onBlur()">
<form (ngSubmit)="onSubmit()">Submit</form>
```

#### Two-way Binding
```html
<input [(ngModel)]="name" placeholder="Votre nom">
<p>Bonjour {{ name }}</p>
```

### Directives Structurelles

#### *ngIf
```html
<div *ngIf="isVisible">Contenu affiché conditionnellement</div>
<div *ngIf="user; else noUser">Bienvenue {{ user.name }}</div>
<ng-template #noUser><p>Aucun utilisateur connecté</p></ng-template>
```

#### *ngFor
```html
<ul>
  <li *ngFor="let user of users; let i = index; let first = first">
    {{ i + 1 }}. {{ user }} 
    <span *ngIf="first">(Premier)</span>
  </li>
</ul>
```

#### *ngSwitch
```html
<div [ngSwitch]="userRole">
  <p *ngSwitchCase="'admin'">Panel Administrateur</p>
  <p *ngSwitchCase="'user'">Dashboard Utilisateur</p>
  <p *ngSwitchDefault>Page d'accueil</p>
</div>
```

### Directives d'Attribut

#### ngClass
```html
<!-- Objet -->
<div [ngClass]="{ 'active': isActive, 'disabled': isDisabled }">Contenu</div>

<!-- String -->
<div [ngClass]="cssClasses">Contenu</div>

<!-- Array -->
<div [ngClass]="['class1', 'class2', dynamicClass]">Contenu</div>
```

#### ngStyle
```html
<div [ngStyle]="{ 
  'color': textColor, 
  'font-size.px': fontSize,
  'background-color': bgColor 
}">
  Texte stylé
</div>
```

---

## 2. TypeScript pour Angular

### Types de Base

```typescript
// Types primitifs
let name: string = 'Angular';
let version: number = 17;
let isStable: boolean = true;
let data: any = { key: 'value' };

// Arrays
let numbers: number[] = [1, 2, 3];
let strings: Array<string> = ['a', 'b', 'c'];

// Objects
let user: { name: string; age: number } = {
  name: 'John',
  age: 30
};
```

### Interfaces

```typescript
interface User {
  id: number;
  name: string;
  email?: string; // Optionnel
  readonly created: Date; // Lecture seule
}

interface ApiResponse<T> {
  data: T;
  message: string;
  status: number;
}

// Utilisation
const user: User = {
  id: 1,
  name: 'Alice',
  created: new Date()
};

const response: ApiResponse<User[]> = {
  data: [user],
  message: 'Success',
  status: 200
};
```

### Classes

```typescript
class Component {
  private _title: string;
  protected version: number;
  public isActive: boolean = true;

  constructor(title: string) {
    this._title = title;
    this.version = 1.0;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  protected updateVersion(): void {
    this.version += 0.1;
  }
}

class MyComponent extends Component {
  constructor(title: string) {
    super(title);
  }

  public increaseVersion(): void {
    this.updateVersion();
  }
}
```

### Generics

```typescript
// Fonction générique
function identity<T>(arg: T): T {
  return arg;
}

// Interface générique
interface Repository<T> {
  findById(id: number): T | null;
  findAll(): T[];
  save(item: T): void;
}

// Classe générique
class DataService<T> implements Repository<T> {
  private items: T[] = [];

  findById(id: number): T | null {
    return this.items.find((item: any) => item.id === id) || null;
  }

  findAll(): T[] {
    return [...this.items];
  }

  save(item: T): void {
    this.items.push(item);
  }
}
```

### Decorators Angular

```typescript
// Composant avec décorateurs
@Component({
  selector: 'app-example',
  templateUrl: './example.component.html',
  styleUrls: ['./example.component.css'],
  providers: [ExampleService]
})
export class ExampleComponent {
  @Input() data: any;
  @Input('customName') value: string;
  @Output() dataChange = new EventEmitter<any>();
  @ViewChild('template') template: TemplateRef<any>;
  @ContentChild(ChildComponent) child: ChildComponent;
  @HostBinding('class.active') isActive = true;
  @HostListener('click', ['$event']) onClick(event: Event) {
    // Gestion du clic
  }
}
```

---

## 3. Architecture et Structure

### Structure de Projet Angular

```
src/
├── app/
│   ├── core/                    # Services singleton, guards
│   │   ├── services/
│   │   ├── guards/
│   │   └── core.module.ts
│   ├── shared/                  # Composants partagés
│   │   ├── components/
│   │   ├── pipes/
│   │   ├── directives/
│   │   └── shared.module.ts
│   ├── features/                # Modules de fonctionnalités
│   │   ├── user/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   ├── models/
│   │   │   └── user.module.ts
│   │   └── admin/
│   ├── layout/                  # Composants de mise en page
│   ├── app-routing.module.ts
│   ├── app.component.ts
│   ├── app.component.html
│   └── app.module.ts
├── assets/
├── environments/
└── styles.css
```

### Module Pattern

```typescript
// Feature Module
@NgModule({
  declarations: [
    UserListComponent,
    UserDetailComponent,
    UserFormComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    ReactiveFormsModule,
    SharedModule
  ],
  providers: [
    UserService,
    UserResolver
  ]
})
export class UserModule { }

// Shared Module
@NgModule({
  declarations: [
    LoaderComponent,
    ConfirmDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    LoaderComponent,
    ConfirmDialogComponent,
    MaterialModule
  ]
})
export class SharedModule { }

// Core Module (Singleton Services)
@NgModule({
  providers: [
    AuthService,
    LoggerService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import only once.');
    }
  }
}
```

### Standalone Components (Angular 14+)

```typescript
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  template: `
    <h1>{{title}}</h1>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'my-app';
}

// Bootstrap avec standalone
import { bootstrapApplication } from '@angular/platform-browser';
import { importProvidersFrom } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(RouterModule.forRoot(routes)),
    // autres providers
  ]
});
```

---

## 4. Services et Dependency Injection

### Création d'un Service

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Singleton au niveau application
})
export class UserService {
  private apiUrl = 'https://api.example.com/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

### Types d'Injection

```typescript
// Dans un composant
export class UserComponent {
  constructor(private userService: UserService) { }
}

// Injection par token
export const API_URL = new InjectionToken<string>('apiUrl');

// Dans le module
providers: [
  { provide: API_URL, useValue: 'https://api.example.com' },
  { provide: UserService, useClass: UserService },
  { provide: LoggerService, useFactory: loggerFactory, deps: [API_URL] },
  { provide: ConfigService, useExisting: AppConfigService }
]

// Dans un composant avec token
constructor(@Inject(API_URL) private apiUrl: string) { }
```

### Hierarchical Injection

```typescript
// Service fourni au niveau component
@Component({
  selector: 'app-user',
  providers: [UserService] // Instance séparée pour ce composant
})
export class UserComponent { }

// Service fourni au niveau module
@NgModule({
  providers: [UserService] // Partagé dans tout le module
})
export class UserModule { }

// Service fourni au niveau root
@Injectable({
  providedIn: 'root' // Singleton global
})
export class UserService { }
```

---

## 5. Routing et Navigation

### Configuration de Base

```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent },
  { path: 'users/:id', component: UserDetailComponent },
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { 
    enableTracing: false, // Pour debug
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### Navigation

```typescript
// Navigation programmatique
import { Router, ActivatedRoute } from '@angular/router';

export class NavigationExample {
  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  navigateToUser(id: number): void {
    this.router.navigate(['/users', id]);
  }

  navigateRelative(): void {
    this.router.navigate(['../sibling'], { relativeTo: this.route });
  }

  navigateWithQueryParams(): void {
    this.router.navigate(['/users'], { 
      queryParams: { page: 1, size: 10 },
      fragment: 'top'
    });
  }

  navigateWithState(): void {
    this.router.navigate(['/users'], { 
      state: { data: 'custom data' } 
    });
  }
}
```

### Navigation dans le Template

```html
<!-- RouterLink basique -->
<a routerLink="/home">Accueil</a>
<a [routerLink]="['/users', userId]">Utilisateur</a>

<!-- Avec paramètres -->
<a [routerLink]="['/users']" 
   [queryParams]="{ page: 1, size: 10 }" 
   [fragment]="'section1'">
   Liste des utilisateurs
</a>

<!-- Active link styling -->
<a routerLink="/home" 
   routerLinkActive="active"
   [routerLinkActiveOptions]="{ exact: true }">
   Accueil
</a>

<!-- Navigation programmatique avec event binding -->
<button (click)="navigateToUser(user.id)">Voir utilisateur</button>
```

### Récupération des Paramètres

```typescript
export class UserDetailComponent implements OnInit {
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    // Paramètres de route (snapshot - valeur unique)
    const id = this.route.snapshot.paramMap.get('id');
    
    // Paramètres de route (observable - valeurs multiples)
    this.route.paramMap.subscribe(params => {
      const userId = params.get('id');
      this.loadUser(userId);
    });

    // Query parameters
    this.route.queryParamMap.subscribe(queryParams => {
      const page = queryParams.get('page') || '1';
      const size = queryParams.get('size') || '10';
    });

    // Fragment
    this.route.fragment.subscribe(fragment => {
      if (fragment) {
        document.getElementById(fragment)?.scrollIntoView();
      }
    });

    // Données résolues
    const userData = this.route.snapshot.data['user'];

    // State de navigation
    const state = history.state;
  }
}
```

### Routes Enfants

```typescript
const routes: Routes = [
  {
    path: 'users',
    component: UserLayoutComponent,
    children: [
      { path: '', component: UserListComponent },
      { path: 'create', component: UserCreateComponent },
      { path: ':id', component: UserDetailComponent },
      { path: ':id/edit', component: UserEditComponent }
    ]
  }
];
```

```html
<!-- user-layout.component.html -->
<div class="user-layout">
  <nav>
    <a routerLink="./create">Créer</a>
    <a routerLink="./">Liste</a>
  </nav>
  <router-outlet></router-outlet> <!-- Composants enfants ici -->
</div>
```

---

## 6. Forms Angular

### Template-Driven Forms

```typescript
// app.module.ts
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule]
})
export class AppModule { }
```

```html
<!-- template-form.component.html -->
<form #userForm="ngForm" (ngSubmit)="onSubmit(userForm)">
  <div>
    <label for="name">Nom:</label>
    <input 
      id="name" 
      name="name" 
      [(ngModel)]="user.name" 
      #name="ngModel" 
      required 
      minlength="3"
      class="form-control"
      [class.is-invalid]="name.invalid && name.touched">
    
    <!-- Messages d'erreur -->
    <div class="invalid-feedback" *ngIf="name.invalid && name.touched">
      <div *ngIf="name.errors?.['required']">Le nom est requis</div>
      <div *ngIf="name.errors?.['minlength']">Le nom doit faire au moins 3 caractères</div>
    </div>
  </div>

  <div>
    <label for="email">Email:</label>
    <input 
      id="email" 
      name="email" 
      type="email" 
      [(ngModel)]="user.email" 
      #email="ngModel" 
      required 
      email
      class="form-control"
      [class.is-invalid]="email.invalid && email.touched">
    
    <div class="invalid-feedback" *ngIf="email.invalid && email.touched">
      <div *ngIf="email.errors?.['required']">L'email est requis</div>
      <div *ngIf="email.errors?.['email']">Format d'email invalide</div>
    </div>
  </div>

  <button type="submit" [disabled]="userForm.invalid">Valider</button>
</form>

<!-- Debug -->
<div>
  <p>Form Valid: {{ userForm.valid }}</p>
  <p>Form Value: {{ userForm.value | json }}</p>
</div>
```

```typescript
// template-form.component.ts
export class TemplateFormComponent {
  user = {
    name: '',
    email: ''
  };

  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log('Form Data:', form.value);
      // Traitement des données
    }
  }
}
```

### Reactive Forms

```typescript
// app.module.ts
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [ReactiveFormsModule]
})
export class AppModule { }
```

```typescript
// reactive-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

export class ReactiveFormComponent implements OnInit {
  userForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      age: ['', [Validators.required, Validators.min(18)]],
      address: this.fb.group({
        street: ['', Validators.required],
        city: ['', Validators.required],
        zipCode: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]]
      }),
      hobbies: this.fb.array([])
    });

    // Écouter les changements
    this.userForm.get('name')?.valueChanges.subscribe(value => {
      console.log('Name changed:', value);
    });
  }

  // Getters pour faciliter l'accès
  get name() { return this.userForm.get('name'); }
  get email() { return this.userForm.get('email'); }
  get hobbies() { return this.userForm.get('hobbies') as FormArray; }

  // Gestion FormArray
  addHobby(): void {
    this.hobbies.push(this.fb.control('', Validators.required));
  }

  removeHobby(index: number): void {
    this.hobbies.removeAt(index);
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      console.log('Form Data:', this.userForm.value);
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.userForm.controls).forEach(key => {
      const control = this.userForm.get(key);
      control?.markAsTouched();
    });
  }
}
```

```html
<!-- reactive-form.component.html -->
<form [formGroup]="userForm" (ngSubmit)="onSubmit()">
  <!-- Champs simples -->
  <div>
    <label for="name">Nom:</label>
    <input 
      id="name" 
      formControlName="name"
      class="form-control"
      [class.is-invalid]="name?.invalid && name?.touched">
    
    <div class="invalid-feedback" *ngIf="name?.invalid && name?.touched">
      <div *ngIf="name?.errors?.['required']">Le nom est requis</div>
      <div *ngIf="name?.errors?.['minlength']">Minimum 3 caractères</div>
    </div>
  </div>

  <!-- FormGroup imbriqué -->
  <div formGroupName="address">
    <h3>Adresse</h3>
    <input formControlName="street" placeholder="Rue">
    <input formControlName="city" placeholder="Ville">
    <input formControlName="zipCode" placeholder="Code postal">
  </div>

  <!-- FormArray -->
  <div>
    <h3>Hobbies</h3>
    <div formArrayName="hobbies">
      <div *ngFor="let hobby of hobbies.controls; let i = index">
        <input [formControlName]="i" placeholder="Hobby">
        <button type="button" (click)="removeHobby(i)">Supprimer</button>
      </div>
    </div>
    <button type="button" (click)="addHobby()">Ajouter hobby</button>
  </div>

  <button type="submit" [disabled]="userForm.invalid">Valider</button>
</form>
```

### Validators Personnalisés

```typescript
// validators.ts
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function customEmailValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const email = control.value;
    if (!email) return null;
    
    const valid = email.includes('@') && email.includes('.');
    return valid ? null : { customEmail: { value: email } };
  };
}

export function passwordStrengthValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value;
    if (!password) return null;
    
    const hasNumber = /[0-9]/.test(password);
    const hasUpper = /[A-Z]/.test(password);
    const hasLower = /[a-z]/.test(password);
    const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    
    const valid = hasNumber && hasUpper && hasLower && hasSpecial && password.length >= 8;
    
    if (!valid) {
      return {
        passwordStrength: {
          hasNumber,
          hasUpper,
          hasLower,
          hasSpecial,
          minLength: password.length >= 8
        }
      };
    }
    
    return null;
  };
}

// Validator asynchrone
export function uniqueEmailValidator(userService: UserService): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    if (!control.value) {
      return of(null);
    }
    
    return userService.checkEmailExists(control.value).pipe(
      map(exists => exists ? { uniqueEmail: true } : null),
      catchError(() => of(null))
    );
  };
}
```

---

## 7. HTTP Client et APIs

### Configuration de Base

```typescript
// app.module.ts
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [HttpClientModule]
})
export class AppModule { }
```

### Service HTTP

```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'https://api.example.com';
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer token'
    })
  };

  constructor(private http: HttpClient) { }

  // GET avec paramètres
  getUsers(page: number = 1, size: number = 10): Observable<ApiResponse<User[]>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponse<User[]>>(`${this.baseUrl}/users`, { params })
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
  }

  // GET par ID
  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/${id}`)
      .pipe(catchError(this.handleError));
  }

  // POST
  createUser(user: CreateUserRequest): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/users`, user, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // PUT
  updateUser(id: number, user: UpdateUserRequest): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/users/${id}`, user, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // PATCH
  patchUser(id: number, updates: Partial<User>): Observable<User> {
    return this.http.patch<User>(`${this.baseUrl}/users/${id}`, updates, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // DELETE
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${id}`)
      .pipe(catchError(this.handleError));
  }

  // Upload de fichier
  uploadFile(file: File): Observable<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<UploadResponse>(`${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    }).pipe(catchError(this.handleError));
  }

  // Téléchargement
  downloadFile(id: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/download/${id}`, {
      responseType: 'blob'
    }).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Une erreur inconnue est survenue';
    
    if (error.error instanceof ErrorEvent) {
      // Erreur côté client
      errorMessage = `Erreur: ${error.error.message}`;
    } else {
      // Erreur côté serveur
      switch (error.status) {
        case 400:
          errorMessage = 'Requête invalide';
          break;
        case 401:
          errorMessage = 'Non autorisé';
          break;
        case 403:
          errorMessage = 'Accès interdit';
          break;
        case 404:
          errorMessage = 'Ressource non trouvée';
          break;
        case 500:
          errorMessage = 'Erreur serveur interne';
          break;
        default:
          errorMessage = `Erreur ${error.status}: ${error.message}`;
      }
    }
    
    console.error('HTTP Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
```

### Interceptors HTTP

```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Ajouter le token d'authentification
    const token = localStorage.getItem('auth_token');
    
    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(authReq);
    }
    
    return next.handle(req);
  }
}

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  constructor(private loadingService: LoadingService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loadingService.setLoading(true);
    
    return next.handle(req).pipe(
      finalize(() => this.loadingService.setLoading(false))
    );
  }
}

// Configuration dans le module
@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

---

## 8. RxJS et Observables

### Observables de Base

```typescript
import { Observable, of, from, interval, timer } from 'rxjs';

// Création d'observables
const simple$ = of(1, 2, 3, 4, 5);
const fromArray$ = from([1, 2, 3, 4, 5]);
const fromPromise$ = from(fetch('/api/data'));
const interval$ = interval(1000); // Émet chaque seconde
const timer$ = timer(2000, 1000); // Démarre après 2s, puis chaque seconde

// Subscription
const subscription = simple$.subscribe({
  next: value => console.log('Next:', value),
  error: error => console.error('Error:', error),
  complete: () => console.log('Complete')
});

// Unsubscribe
subscription.unsubscribe();
```

### Opérateurs de Transformation

```typescript
import { map, mergeMap, switchMap, concatMap, exhaustMap } from 'rxjs/operators';

// map - Transforme chaque valeur
const mapped$ = source$.pipe(
  map(x => x * 2)
);

// mergeMap - Aplatit les observables (peut entrelacer)
const merged$ = source$.pipe(
  mergeMap(id => this.http.get(`/api/users/${id}`))
);

// switchMap - Annule la requête précédente
const switched$ = searchTerm$.pipe(
  debounceTime(300),
  switchMap(term => this.searchService.search(term))
);

// concatMap - Exécute séquentiellement
const concatenated$ = ids$.pipe(
  concatMap(id => this.http.get(`/api/users/${id}`))
);

// exhaustMap - Ignore les nouvelles valeurs jusqu'à completion
const exhausted$ = clicks$.pipe(
  exhaustMap(() => this.http.post('/api/save', data))
);
```

### Opérateurs de Filtrage

```typescript
import { filter, take, takeUntil, takeWhile, skip, debounceTime, distinctUntilChanged } from 'rxjs/operators';

// filter - Filtre selon une condition
const filtered$ = source$.pipe(
  filter(x => x > 5)
);

// take - Prend les N premiers éléments
const firstThree$ = source$.pipe(
  take(3)
);

// takeUntil - Prend jusqu'à ce qu'un autre observable émette
const untilDestroy$ = source$.pipe(
  takeUntil(this.destroy$)
);

// debounceTime - Délai après la dernière émission
const debounced$ = searchInput$.pipe(
  debounceTime(300)
);

// distinctUntilChanged - Supprime les doublons consécutifs
const distinct$ = source$.pipe(
  distinctUntilChanged()
);
```

### Opérateurs de Combinaison

```typescript
import { combineLatest, merge, concat, forkJoin, zip } from 'rxjs';
import { startWith, withLatestFrom } from 'rxjs/operators';

// combineLatest - Combine les dernières valeurs
const combined$ = combineLatest([
  this.userService.currentUser$,
  this.settingsService.settings$
]).pipe(
  map(([user, settings]) => ({ user, settings }))
);

// merge - Fusionne plusieurs observables
const merged$ = merge(
  source1$,
  source2$,
  source3$
);

// forkJoin - Attend que tous les observables se terminent
const allData$ = forkJoin({
  users: this.userService.getUsers(),
  products: this.productService.getProducts(),
  settings: this.settingsService.getSettings()
});

// withLatestFrom - Combine avec la dernière valeur d'un autre observable
const withLatest$ = trigger$.pipe(
  withLatestFrom(this.dataService.data$),
  map(([trigger, data]) => ({ trigger, data }))
);
```

### Gestion d'État avec BehaviorSubject

```typescript
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StateService {
  private userSubject = new BehaviorSubject<User | null>(null);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private errorsSubject = new Subject<string>();

  // Public observables
  user$ = this.userSubject.asObservable();
  loading$ = this.loadingSubject.asObservable();
  errors$ = this.errorsSubject.asObservable();

  // Getters pour valeur actuelle
  get currentUser(): User | null {
    return this.userSubject.value;
  }

  // Actions
  setUser(user: User | null): void {
    this.userSubject.next(user);
  }

  setLoading(loading: boolean): void {
    this.loadingSubject.next(loading);
  }

  addError(error: string): void {
    this.errorsSubject.next(error);
  }

  // State combiné
  state$ = combineLatest([
    this.user$,
    this.loading$
  ]).pipe(
    map(([user, loading]) => ({ user, loading }))
  );
}
```

### Patterns Courants

```typescript
// Unsubscribe automatique
export class ComponentWithObservables implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    // Pattern takeUntil
    this.dataService.getData().pipe(
      takeUntil(this.destroy$)
    ).subscribe(data => {
      // Traitement des données
    });

    // Pattern async/await avec firstValueFrom
    this.loadDataAsync();
  }

  async loadDataAsync(): Promise<void> {
    try {
      const data = await firstValueFrom(
        this.dataService.getData().pipe(
          timeout(5000),
          catchError(error => of(null))
        )
      );
      
      if (data) {
        this.processData(data);
      }
    } catch (error) {
      console.error('Error loading data:', error);
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

// Cache avec shareReplay
@Injectable()
export class CachedDataService {
  private cache$ = this.http.get('/api/config').pipe(
    shareReplay({ bufferSize: 1, refCount: true })
  );

  getConfig(): Observable<Config> {
    return this.cache$;
  }
}
```

---

## 9. Lifecycle Hooks

### Ordre d'Exécution des Hooks

1. **ngOnChanges** - Changements des propriétés d'entrée
2. **ngOnInit** - Initialisation du composant
3. **ngDoCheck** - Détection manuelle des changements
4. **ngAfterContentInit** - Après projection du contenu
5. **ngAfterContentChecked** - Après vérification du contenu
6. **ngAfterViewInit** - Après initialisation de la vue
7. **ngAfterViewChecked** - Après vérification de la vue
8. **ngOnDestroy** - Avant destruction du composant

### Implémentation des Hooks

```typescript
import { 
  Component, OnInit, OnDestroy, OnChanges, DoCheck, 
  AfterContentInit, AfterContentChecked, AfterViewInit, AfterViewChecked,
  SimpleChanges, Input
} from '@angular/core';

@Component({
  selector: 'app-lifecycle',
  template: `
    <h3>{{ title }}</h3>
    <p>Count: {{ count }}</p>
    <ng-content></ng-content>
  `
})
export class LifecycleComponent implements 
  OnInit, OnDestroy, OnChanges, DoCheck,
  AfterContentInit, AfterContentChecked, 
  AfterViewInit, AfterViewChecked {

  @Input() title: string = '';
  @Input() count: number = 0;

  private previousCount: number = 0;

  constructor() {
    console.log('Constructor called');
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('ngOnChanges called', changes);
    
    if (changes['title']) {
      console.log('Title changed from', changes['title'].previousValue, 'to', changes['title'].currentValue);
    }
    
    if (changes['count']) {
      console.log('Count changed from', changes['count'].previousValue, 'to', changes['count'].currentValue);
    }
  }

  ngOnInit(): void {
    console.log('ngOnInit called');
    // Initialisation des données
    this.loadInitialData();
  }

  ngDoCheck(): void {
    console.log('ngDoCheck called');
    // Détection personnalisée des changements
    if (this.count !== this.previousCount) {
      console.log('Count changed detected in DoCheck');
      this.previousCount = this.count;
    }
  }

  ngAfterContentInit(): void {
    console.log('ngAfterContentInit called');
    // Le contenu projeté est initialisé
  }

  ngAfterContentChecked(): void {
    console.log('ngAfterContentChecked called');
    // Le contenu projeté est vérifié
  }

  ngAfterViewInit(): void {
    console.log('ngAfterViewInit called');
    // La vue du composant est initialisée
    // Idéal pour l'initialisation du DOM
  }

  ngAfterViewChecked(): void {
    console.log('ngAfterViewChecked called');
    // La vue du composant est vérifiée
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy called');
    // Nettoyage des ressources
    this.cleanup();
  }

  private loadInitialData(): void {
    // Logique d'initialisation
  }

  private cleanup(): void {
    // Annuler les subscriptions, timers, etc.
  }
}
```

### Cas d'Usage Pratiques

```typescript
// Composant avec gestion complète du cycle de vie
@Component({
  selector: 'app-user-detail',
  template: `<div>User: {{ user?.name }}</div>`
})
export class UserDetailComponent implements OnInit, OnDestroy, OnChanges {
  @Input() userId: string;
  user: User | null = null;
  
  private destroy$ = new Subject<void>();
  private subscription: Subscription = new Subscription();

  constructor(
    private userService: UserService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['userId'] && !changes['userId'].firstChange) {
      this.loadUser();
    }
  }

  ngOnInit(): void {
    this.loadUser();
    this.setupRealtimeUpdates();
  }

  ngOnDestroy(): void {
    // Méthode 1: Subject avec takeUntil
    this.destroy$.next();
    this.destroy$.complete();
    
    // Méthode 2: Subscription manuelle
    this.subscription.unsubscribe();
  }

  private loadUser(): void {
    if (this.userId) {
      this.userService.getUserById(this.userId)
        .pipe(takeUntil(this.destroy$))
        .subscribe(user => {
          this.user = user;
          this.cdr.detectChanges(); // Force la détection si nécessaire
        });
    }
  }

  private setupRealtimeUpdates(): void {
    const sub = this.userService.getUserUpdates(this.userId)
      .subscribe(updatedUser => {
        this.user = updatedUser;
      });
    
    this.subscription.add(sub);
  }
}
```

---

## 10. Pipes

### Pipes Intégrés

```html
<!-- Formatage de texte -->
<p>{{ name | uppercase }}</p>
<p>{{ name | lowercase }}</p>
<p>{{ name | titlecase }}</p>

<!-- Formatage de nombres -->
<p>{{ price | currency:'EUR':'symbol':'1.2-2':'fr' }}</p>
<p>{{ percentage | percent:'1.2-2' }}</p>
<p>{{ fileSize | number:'1.0-2' }} MB</p>

<!-- Formatage de dates -->
<p>{{ today | date:'short' }}</p>
<p>{{ today | date:'dd/MM/yyyy' }}</p>
<p>{{ today | date:'EEEE, MMMM d, y':'fr' }}</p>

<!-- Pipes de transformation -->
<p>{{ data | json }}</p>
<p>{{ items | slice:0:5 }}</p>

<!-- Pipe async -->
<p>{{ user$ | async | json }}</p>
<div *ngIf="loading$ | async">Chargement...</div>
```

### Pipes Personnalisés

```typescript
// Simple pipe de transformation
@Pipe({ name: 'reverse' })
export class ReversePipe implements PipeTransform {
  transform(value: string): string {
    return value ? value.split('').reverse().join('') : '';
  }
}

// Pipe avec paramètres
@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50, trail: string = '...'): string {
    if (!value) return '';
    
    return value.length > limit 
      ? value.substring(0, limit) + trail
      : value;
  }
}

// Pipe de filtrage (non recommandé pour les performances)
@Pipe({ name: 'filter' })
export class FilterPipe implements PipeTransform {
  transform<T>(items: T[], searchTerm: string, property: keyof T): T[] {
    if (!items || !searchTerm) {
      return items;
    }

    return items.filter(item => 
      String(item[property]).toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
}

// Pipe pure vs impure
@Pipe({ 
  name: 'expensiveCalculation',
  pure: false // Recalcule à chaque détection de changement
})
export class ExpensiveCalculationPipe implements PipeTransform {
  transform(value: number[]): number {
    console.log('Expensive calculation running...');
    return value.reduce((sum, num) => sum + Math.pow(num, 2), 0);
  }
}
```

### Utilisation des Pipes

```html
<!-- Pipes simples -->
<p>{{ text | reverse }}</p>
<p>{{ longText | truncate:30:'...' }}</p>

<!-- Chaînage de pipes -->
<p>{{ user.name | truncate:20 | uppercase }}</p>
<p>{{ price | currency:'EUR' | lowercase }}</p>

<!-- Pipe avec observables -->
<div *ngFor="let user of users$ | async | filter:searchTerm:'name'">
  {{ user.name }}
</div>

<!-- Pipe dans les templates -->
<ng-container *ngFor="let item of items; let even = even">
  <div [ngClass]="{ 'even-row': even | toString }">
    {{ item.name }}
  </div>
</ng-container>
```

### Pipes Utiles pour l'Internationalisation

```typescript
@Pipe({ name: 'translate' })
export class TranslatePipe implements PipeTransform {
  constructor(private translateService: TranslateService) { }

  transform(key: string, params?: any): string {
    return this.translateService.translate(key, params);
  }
}

@Pipe({ name: 'localizedDate' })
export class LocalizedDatePipe implements PipeTransform {
  transform(value: Date, locale: string = 'fr-FR'): string {
    return value.toLocaleDateString(locale, {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }
}
```

---

## 11. Directives

### Directives d'Attribut Personnalisées

```typescript
import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

// Directive simple de highlighting
@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input() appHighlight: string = 'yellow';
  @Input() defaultColor: string = 'transparent';

  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  @HostListener('mouseenter') onMouseEnter() {
    this.highlight(this.appHighlight);
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.highlight(this.defaultColor);
  }

  private highlight(color: string): void {
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', color);
  }
}

// Directive de validation
@Directive({
  selector: '[appEmailValidator]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: EmailValidatorDirective, multi: true }
  ]
})
export class EmailValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isValid = emailRegex.test(control.value);
    
    return isValid ? null : { invalidEmail: { value: control.value } };
  }
}

// Directive avec services injectés
@Directive({
  selector: '[appLazyLoad]'
})
export class LazyLoadDirective implements OnInit, OnDestroy {
  @Input() appLazyLoad: string = '';
  
  private observer?: IntersectionObserver;

  constructor(
    private el: ElementRef,
    private renderer: Renderer2,
    private imageService: ImageService
  ) { }

  ngOnInit(): void {
    this.setupIntersectionObserver();
  }

  ngOnDestroy(): void {
    if (this.observer) {
      this.observer.disconnect();
    }
  }

  private setupIntersectionObserver(): void {
    this.observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.loadImage();
          this.observer?.unobserve(this.el.nativeElement);
        }
      });
    });

    this.observer.observe(this.el.nativeElement);
  }

  private loadImage(): void {
    const img = this.el.nativeElement;
    this.renderer.setAttribute(img, 'src', this.appLazyLoad);
    this.renderer.addClass(img, 'loaded');
  }
}
```

### Directives Structurelles

```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

// Directive *appIf personnalisée
@Directive({
  selector: '[appIf]'
})
export class IfDirective {
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }

  @Input() set appIf(condition: boolean) {
    if (condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (!condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
}

// Directive *appFor avec fonctionnalités avancées
@Directive({
  selector: '[appFor][appForOf]'
})
export class ForDirective<T> implements OnChanges {
  @Input() appForOf: T[] = [];
  @Input() appForTrackBy?: (index: number, item: T) => any;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['appForOf']) {
      this.updateView();
    }
  }

  private updateView(): void {
    this.viewContainer.clear();
    
    this.appForOf.forEach((item, index) => {
      const context = {
        $implicit: item,
        index,
        count: this.appForOf.length,
        first: index === 0,
        last: index === this.appForOf.length - 1,
        even: index % 2 === 0,
        odd: index % 2 === 1
      };
      
      this.viewContainer.createEmbeddedView(this.templateRef, context);
    });
  }
}

// Directive avec permissions
@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective implements OnInit, OnDestroy {
  @Input() appHasRole: string[] = [];
  
  private subscription?: Subscription;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.subscription = this.authService.currentUser$.subscribe(user => {
      this.updateView(user);
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  private updateView(user: User | null): void {
    const hasRequiredRole = user && this.appHasRole.some(role => 
      user.roles.includes(role)
    );

    if (hasRequiredRole) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
```

### Utilisation des Directives

```html
<!-- Directive d'attribut -->
<p appHighlight="lightblue" defaultColor="white">
  Texte avec highlight au survol
</p>

<!-- Directive de validation -->
<input type="email" appEmailValidator [(ngModel)]="email">

<!-- Directive de lazy loading -->
<img appLazyLoad="/assets/images/placeholder.jpg" alt="Image">

<!-- Directive structurelle personnalisée -->
<div *appIf="showContent">
  Contenu conditionnel
</div>

<div *appFor="let item of items; trackBy: trackByFn; let i = index">
  {{ i }}: {{ item.name }}
</div>

<div *appHasRole="['admin', 'moderator']">
  Contenu réservé aux admins et modérateurs
</div>
```

---

## 12. Testing Angular

### Configuration de Base

```typescript
// karma.conf.js
module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage')
    ],
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage'),
      subdir: '.',
      reporters: [
        { type: 'html' },
        { type: 'text-summary' },
        { type: 'lcov' }
      ]
    },
    reporters: ['progress', 'kjhtml', 'coverage'],
    browsers: ['Chrome'],
    restartOnFileChange: true
  });
};
```

### Test de Composants

```typescript
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserComponent],
      imports: [CommonModule, FormsModule],
      providers: [
        { provide: UserService, useClass: MockUserService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    compiled = fixture.nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render title', () => {
    component.title = 'Test Title';
    fixture.detectChanges();
    expect(compiled.querySelector('h1')?.textContent).toContain('Test Title');
  });

  it('should handle click event', () => {
    spyOn(component, 'onClick');
    const button = fixture.debugElement.query(By.css('button'));
    button.nativeElement.click();
    expect(component.onClick).toHaveBeenCalled();
  });

  it('should display user list', async () => {
    const mockUsers = [
      { id: 1, name: 'User 1' },
      { id: 2, name: 'User 2' }
    ];
    
    component.users = mockUsers;
    fixture.detectChanges();
    
    const userElements = fixture.debugElement.queryAll(By.css('.user-item'));
    expect(userElements.length).toBe(2);
    expect(userElements[0].nativeElement.textContent).toContain('User 1');
  });

  it('should emit event when user is selected', () => {
    spyOn(component.userSelected, 'emit');
    const user = { id: 1, name: 'Test User' };
    
    component.selectUser(user);
    
    expect(component.userSelected.emit).toHaveBeenCalledWith(user);
  });
});
```

### Test de Services

```typescript
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch users', () => {
    const mockUsers = [
      { id: 1, name: 'User 1', email: 'user1@example.com' },
      { id: 2, name: 'User 2', email: 'user2@example.com' }
    ];

    service.getUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
      expect(users.length).toBe(2);
    });

    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });

  it('should handle error', () => {
    service.getUsers().subscribe(
      users => fail('Should have failed'),
      error => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne('/api/users');
    req.flush('Error', { status: 500, statusText: 'Server Error' });
  });

  it('should create user', () => {
    const newUser = { name: 'New User', email: 'new@example.com' };
    const createdUser = { id: 3, ...newUser };

    service.createUser(newUser).subscribe(user => {
      expect(user).toEqual(createdUser);
    });

    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newUser);
    req.flush(createdUser);
  });
});
```

### Mocks et Spies

```typescript
// Mock de service
class MockUserService {
  getUsers() {
    return of([
      { id: 1, name: 'Mock User 1' },
      { id: 2, name: 'Mock User 2' }
    ]);
  }

  getUserById(id: number) {
    return of({ id, name: `Mock User ${id}` });
  }
}

// Tests avec spies
describe('UserService with spies', () => {
  let service: UserService;
  let httpSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    const spy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);

    TestBed.configureTestingModule({
      providers: [
        UserService,
        { provide: HttpClient, useValue: spy }
      ]
    });

    service = TestBed.inject(UserService);
    httpSpy = TestBed.inject(HttpClient) as jasmine.SpyObj<HttpClient>;
  });

  it('should return users', () => {
    const mockUsers = [{ id: 1, name: 'Test User' }];
    httpSpy.get.and.returnValue(of(mockUsers));

    service.getUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });

    expect(httpSpy.get).toHaveBeenCalledWith('/api/users');
  });
});
```

### Tests d'Intégration

```typescript
describe('UserListComponent Integration', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserListComponent, UserItemComponent],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [UserService]
    }).compileComponents();

    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
  });

  it('should load and display users', fakeAsync(() => {
    const mockUsers = [
      { id: 1, name: 'User 1', email: 'user1@example.com' },
      { id: 2, name: 'User 2', email: 'user2@example.com' }
    ];

    spyOn(userService, 'getUsers').and.returnValue(of(mockUsers));

    component.ngOnInit();
    tick();
    fixture.detectChanges();

    const userElements = fixture.debugElement.queryAll(By.css('app-user-item'));
    expect(userElements.length).toBe(2);
  }));
});
```

### Tests avec Observables

```typescript
import { cold, hot } from 'jasmine-marbles';

describe('UserService with marble testing', () => {
  it('should debounce search', () => {
    const searchTerm$ = hot('-a-b-c----|', {
      a: 'test',
      b: 'testing',
      c: 'test again'
    });

    const expected$ = cold('-----c----|', {
      c: 'test again'
    });

    const result$ = searchTerm$.pipe(
      debounceTime(500, getTestScheduler())
    );

    expect(result$).toBeObservable(expected$);
  });
});
```

---

## 13. Performance et Optimisation

### OnPush Change Detection

```typescript
import { Component, ChangeDetectionStrategy, Input } from '@angular/core';

@Component({
  selector: 'app-user-list',
  template: `
    <div *ngFor="let user of users; trackBy: trackByFn">
      <app-user-item [user]="user" (userClick)="onUserClick($event)"></app-user-item>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserListComponent {
  @Input() users: User[] = [];

  constructor(private cdr: ChangeDetectorRef) { }

  trackByFn(index: number, item: User): any {
    return item.id;
  }

  onUserClick(user: User): void {
    // Logique de traitement
    // Si nécessaire, forcer la détection
    this.cdr.detectChanges();
  }

  // Méthode pour forcer la détection si nécessaire
  updateData(newUsers: User[]): void {
    this.users = newUsers;
    this.cdr.markForCheck();
  }
}
```

### Lazy Loading

```typescript
// Lazy loading de modules
const routes: Routes = [
  {
    path: 'users',
    loadChildren: () => import('./users/users.module').then(m => m.UsersModule)
  },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canLoad: [AdminGuard]
  }
];

// Lazy loading de composants (Angular 14+)
const routes: Routes = [
  {
    path: 'profile',
    loadComponent: () => import('./profile/profile.component').then(c => c.ProfileComponent)
  }
];

// Preloading personnalisé
export class CustomPreloadingStrategy implements PreloadingStrategy {
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    if (route.data && route.data['preload']) {
      return load();
    }
    return of(null);
  }
}

// Configuration dans le routing
RouterModule.forRoot(routes, {
  preloadingStrategy: CustomPreloadingStrategy
})
```

### Optimisation des Templates

```html
<!-- Utilisez trackBy avec *ngFor -->
<div *ngFor="let item of items; trackBy: trackByFn">
  {{ item.name }}
</div>

<!-- Évitez les fonctions dans les templates -->
<!-- ❌ Mauvais -->
<div *ngFor="let item of getFilteredItems()">{{ item.name }}</div>

<!-- ✅ Bon -->
<div *ngFor="let item of filteredItems">{{ item.name }}</div>

<!-- Utilisez l'async pipe -->
<!-- ❌ Mauvais -->
<div *ngIf="loading">Loading...</div>

<!-- ✅ Bon -->
<div *ngIf="loading$ | async">Loading...</div>

<!-- OnPush avec immutable data -->
<app-child 
  [data]="data$ | async" 
  (dataChange)="updateData($event)">
</app-child>
```

### Optimisation des Images

```typescript
// Directive de lazy loading d'images
@Directive({
  selector: '[appLazyImg]'
})
export class LazyImgDirective implements OnInit, OnDestroy {
  @Input() appLazyImg: string = '';
  @Input() placeholder: string = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMSIgaGVpZ2h0PSIxIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPjxyZWN0IHdpZHRoPSIxMDAlIiBoZWlnaHQ9IjEwMCUiIGZpbGw9IiNjY2MiLz48L3N2Zz4=';

  private observer?: IntersectionObserver;

  constructor(private el: ElementRef, private renderer: Renderer2) { }

  ngOnInit(): void {
    // Définir placeholder
    this.renderer.setAttribute(this.el.nativeElement, 'src', this.placeholder);
    
    // Observer intersection
    this.observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.loadImage();
        }
      });
    }, { threshold: 0.1 });

    this.observer.observe(this.el.nativeElement);
  }

  ngOnDestroy(): void {
    this.observer?.disconnect();
  }

  private loadImage(): void {
    const img = new Image();
    img.onload = () => {
      this.renderer.setAttribute(this.el.nativeElement, 'src', this.appLazyImg);
      this.observer?.unobserve(this.el.nativeElement);
    };
    img.src = this.appLazyImg;
  }
}
```

### Bundle Analysis et Tree Shaking

```bash
# Analyse du bundle
ng build --stats-json
npx webpack-bundle-analyzer dist/stats.json

# Build de production optimisé
ng build --prod --build-optimizer --aot

# Mesure des performances
ng add @angular/pwa
ng build --prod --service-worker
```

### Virtual Scrolling

```typescript
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-virtual-list',
  template: `
    <cdk-virtual-scroll-viewport 
      itemSize="50" 
      class="viewport"
      (scrolledIndexChange)="onScrollIndexChange($event)">
      <div *cdkVirtualFor="let item of items; trackBy: trackByFn" class="item">
        {{ item.name }}
      </div>
    </cdk-virtual-scroll-viewport>
  `,
  styles: [`
    .viewport {
      height: 400px;
      width: 100%;
    }
    .item {
      height: 50px;
      display: flex;
      align-items: center;
      padding: 0 16px;
      border-bottom: 1px solid #ccc;
    }
  `]
})
export class VirtualListComponent {
  items = Array.from({length: 100000}, (_, i) => ({
    id: i,
    name: `Item ${i}`
  }));

  trackByFn(index: number, item: any): number {
    return item.id;
  }

  onScrollIndexChange(index: number): void {
    // Logique de chargement dynamique
    if (index > this.items.length - 10) {
      this.loadMoreItems();
    }
  }

  loadMoreItems(): void {
    // Charger plus d'éléments
  }
}
```

---

## 14. Angular CLI

### Commandes Essentielles

```bash
# Installation et création
npm install -g @angular/cli
ng new my-app --routing --style=scss --skip-git
cd my-app
ng serve --open --port=4200

# Génération d'éléments
ng generate component user-list --skip-tests
ng generate service shared/user --skip-tests
ng generate module shared --routing
ng generate pipe shared/truncate
ng generate directive shared/highlight
ng generate guard auth/auth
ng generate interceptor auth/auth
ng generate resolver user/user
ng generate class models/user
ng generate interface models/user-response
ng generate enum models/user-status

# Alias courts
ng g c components/header
ng g s services/data
ng g m features/user --routing
ng g p pipes/custom
ng g d directives/highlight
ng g g guards/auth
ng g i interceptors/logging
ng g r resolvers/user
```

### Configuration Angular.json

```json
{
  "projects": {
    "my-app": {
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:browser",
          "options": {
            "outputPath": "dist/my-app",
            "index": "src/index.html",
            "main": "src/main.ts",
            "polyfills": ["zone.js"],
            "tsConfig": "tsconfig.app.json",
            "assets": [
              "src/favicon.ico",
              "src/assets"
            ],
            "styles": [
              "src/styles.scss"
            ],
            "scripts": [],
            "buildOptimizer": true,
            "aot": true,
            "extractCss": true,
            "namedChunks": false,
            "extractLicenses": true,
            "vendorChunk": false,
            "budgets": [
              {
                "type": "initial",
                "maximumWarning": "2mb",
                "maximumError": "5mb"
              }
            ]
          },
          "configurations": {
            "production": {
              "fileReplacements": [
                {
                  "replace": "src/environments/environment.ts",
                  "with": "src/environments/environment.prod.ts"
                }
              ],
              "optimization": true,
              "outputHashing": "all",
              "sourceMap": false,
              "namedChunks": false,
              "extractLicenses": true,
              "vendorChunk": false,
              "buildOptimizer": true
            }
          }
        },
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "options": {
            "proxyConfig": "proxy.conf.json"
          }
        },
        "test": {
          "builder": "@angular-devkit/build-angular:karma",
          "options": {
            "main": "src/test.ts",
            "polyfills": ["zone.js/testing"],
            "tsConfig": "tsconfig.spec.ts",
            "karmaConfig": "karma.conf.js",
            "assets": [
              "src/favicon.ico",
              "src/assets"
            ],
            "styles": [
              "src/styles.scss"
            ],
            "scripts": [],
            "codeCoverage": true
          }
        }
      }
    }
  }
}
```

### Configuration de Proxy

```json
// proxy.conf.json
{
  "/api/*": {
    "target": "http://localhost:3000",
    "secure": true,
    "changeOrigin": true,
    "logLevel": "debug",
    "headers": {
      "X-Custom-Header": "value"
    }
  },
  "/auth/*": {
    "target": "http://localhost:3001",
    "secure": true,
    "changeOrigin": true
  }
}
```

### Scripts NPM Personnalisés

```json
// package.json
{
  "scripts": {
    "start": "ng serve",
    "start:prod": "ng serve --configuration production",
    "build": "ng build",
    "build:prod": "ng build --configuration production",
    "test": "ng test",
    "test:ci": "ng test --watch=false --code-coverage",
    "e2e": "ng e2e",
    "lint": "ng lint",
    "lint:fix": "ng lint --fix",
    "analyze": "ng build --stats-json && npx webpack-bundle-analyzer dist/stats.json",
    "serve:ssr": "node dist/server/main.js"
  }
}
```

---

## 15. Guards et Interceptors

### Route Guards

```typescript
// CanActivate Guard
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | boolean {
    
    return this.authService.isAuthenticated().pipe(
      map(isAuth => {
        if (isAuth) {
          return true;
        } else {
          this.router.navigate(['/login'], { 
            queryParams: { returnUrl: state.url } 
          });
          return false;
        }
      })
    );
  }
}

// CanActivateChild Guard
@Injectable()
export class AdminGuard implements CanActivateChild {
  constructor(private authService: AuthService) { }

  canActivateChild(): Observable<boolean> {
    return this.authService.hasRole('admin');
  }
}

// CanDeactivate Guard
export interface CanComponentDeactivate {
  canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
}

@Injectable()
export class UnsavedChangesGuard implements CanDeactivate<CanComponentDeactivate> {
  canDeactivate(
    component: CanComponentDeactivate
  ): Observable<boolean> | Promise<boolean> | boolean {
    
    if (component.canDeactivate && !component.canDeactivate()) {
      return confirm('Vous avez des modifications non sauvegardées. Voulez-vous vraiment quitter ?');
    }
    return true;
  }
}

// CanLoad Guard
@Injectable()
export class FeatureGuard implements CanLoad {
  constructor(private featureService: FeatureService) { }

  canLoad(route: Route): Observable<boolean> {
    return this.featureService.isFeatureEnabled(route.path || '');
  }
}

// Resolve Guard
@Injectable()
export class UserResolver implements Resolve<User> {
  constructor(private userService: UserService) { }

  resolve(
    route: ActivatedRouteSnapshot
  ): Observable<User> | Promise<User> | User {
    const userId = route.paramMap.get('id');
    
    if (userId) {
      return this.userService.getUserById(+userId).pipe(
        catchError(error => {
          // Gérer l'erreur et rediriger si nécessaire
          return of({} as User);
        })
      );
    }
    
    return of({} as User);
  }
}
```

### HTTP Interceptors

```typescript
// Auth Interceptor
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    
    const token = this.authService.getToken();
    
    if (token) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(authReq);
    }
    
    return next.handle(req);
  }
}

// Error Interceptor
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private notificationService: NotificationService,
    private router: Router
  ) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Une erreur est survenue';
        
        if (error.error instanceof ErrorEvent) {
          // Erreur côté client
          errorMessage = error.error.message;
        } else {
          // Erreur côté serveur
          switch (error.status) {
            case 401:
              this.router.navigate(['/login']);
              errorMessage = 'Session expirée';
              break;
            case 403:
              errorMessage = 'Accès interdit';
              break;
            case 404:
              errorMessage = 'Ressource non trouvée';
              break;
            case 500:
              errorMessage = 'Erreur serveur';
              break;
          }
        }
        
        this.notificationService.showError(errorMessage);
        return throwError(() => error);
      })
    );
  }
}

// Loading Interceptor
@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  private activeRequests = 0;
  
  constructor(private loadingService: LoadingService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    
    // Ignorer certaines requêtes
    if (req.headers.get('ignoreLoader')) {
      return next.handle(req);
    }
    
    this.activeRequests++;
    this.loadingService.setLoading(true);
    
    return next.handle(req).pipe(
      finalize(() => {
        this.activeRequests--;
        if (this.activeRequests === 0) {
          this.loadingService.setLoading(false);
        }
      })
    );
  }
}

// Cache Interceptor
@Injectable()
export class CacheInterceptor implements HttpInterceptor {
  private cache = new Map<string, HttpResponse<any>>();
  
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    
    // Seulement GET et avec header cache
    if (req.method !== 'GET' || !req.headers.get('cache')) {
      return next.handle(req);
    }
    
    const cachedResponse = this.cache.get(req.url);
    
    if (cachedResponse) {
      return of(cachedResponse);
    }
    
    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          this.cache.set(req.url, event);
        }
      })
    );
  }
}
```

### Configuration des Guards et Interceptors

```typescript
// Route configuration avec guards
const routes: Routes = [
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canLoad: [FeatureGuard],
    canActivate: [AuthGuard],
    canActivateChild: [AdminGuard]
  },
  {
    path: 'user/:id',
    component: UserDetailComponent,
    canActivate: [AuthGuard],
    canDeactivate: [UnsavedChangesGuard],
    resolve: {
      user: UserResolver
    }
  }
];

// Module configuration
@NgModule({
  providers: [
    // Guards
    AuthGuard,
    AdminGuard,
    UnsavedChangesGuard,
    FeatureGuard,
    UserResolver,
    
    // Interceptors
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true
    }
  ]
})
export class CoreModule { }
```

---

## 16. Modules et Lazy Loading

### Structure des Modules

```typescript
// Core Module (Singleton Services)
@NgModule({
  providers: [
    AuthService,
    ErrorService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule should be imported only in AppModule');
    }
  }
}

// Shared Module (Composants Réutilisables)
@NgModule({
  declarations: [
    LoaderComponent,
    ConfirmDialogComponent,
    TruncatePipe,
    HighlightDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    // Modules exportés
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    
    // Composants exportés
    LoaderComponent,
    ConfirmDialogComponent,
    TruncatePipe,
    HighlightDirective
  ]
})
export class SharedModule { }

// Feature Module
@NgModule({
  declarations: [
    UserListComponent,
    UserDetailComponent,
    UserFormComponent
  ],
  imports: [
    SharedModule,
    UserRoutingModule
  ],
  providers: [
    UserService,
    UserResolver
  ]
})
export class UserModule { }
```

### Lazy Loading Configuration

```typescript
// App Routing avec Lazy Loading
const routes: Routes = [
  { 
    path: '', 
    redirectTo: '/home', 
    pathMatch: 'full' 
  },
  {
    path: 'home',
    loadChildren: () => import('./features/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'users',
    loadChildren: () => import('./features/users/users.module').then(m => m.UsersModule),
    canLoad: [AuthGuard]
  },
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin.module').then(m => m.AdminModule),
    canLoad: [AuthGuard, AdminGuard],
    data: { preload: true }
  },
  {
    path: '**',
    loadChildren: () => import('./features/not-found/not-found.module').then(m => m.NotFoundModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    enableTracing: false,
    preloadingStrategy: PreloadAllModules,
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### Preloading Strategies

```typescript
// Custom Preloading Strategy
@Injectable()
export class CustomPreloadingStrategy implements PreloadingStrategy {
  
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    // Preloader selon les données de route
    if (route.data && route.data['preload']) {
      console.log('Preloading module:', route.path);
      return load();
    }
    
    // Preloader selon la connexion réseau
    if (this.isGoodConnection()) {
      return load();
    }
    
    return of(null);
  }
  
  private isGoodConnection(): boolean {
    const connection = (navigator as any).connection;
    if (connection) {
      return connection.effectiveType === '4g';
    }
    return true;
  }
}

// Selective Preloading
@Injectable()
export class SelectivePreloadingStrategy implements PreloadingStrategy {
  preloadedModules: string[] = [];
  
  preload(route: Route, load: () => Observable<any>): Observable<any> {
    if (route.data && route.data['preload']) {
      this.preloadedModules.push(route.path!);
      console.log('Preloaded modules:', this.preloadedModules);
      return load();
    }
    
    return of(null);
  }
}
```

### Module avec Providers Configurables

```typescript
// Service avec configuration
export interface LoggerConfig {
  level: 'debug' | 'info' | 'warn' | 'error';
  enableConsole: boolean;
  enableRemote: boolean;
  remoteUrl?: string;
}

export const LOGGER_CONFIG = new InjectionToken<LoggerConfig>('logger.config');

@Injectable()
export class LoggerService {
  constructor(@Inject(LOGGER_CONFIG) private config: LoggerConfig) { }
  
  log(level: string, message: string): void {
    if (this.config.enableConsole) {
      console.log(`[${level}] ${message}`);
    }
    
    if (this.config.enableRemote && this.config.remoteUrl) {
      // Envoyer vers serveur distant
    }
  }
}

// Module avec forRoot/forChild pattern
@NgModule({
  declarations: [LoggerComponent],
  exports: [LoggerComponent]
})
export class LoggerModule {
  
  static forRoot(config: LoggerConfig): ModuleWithProviders<LoggerModule> {
    return {
      ngModule: LoggerModule,
      providers: [
        LoggerService,
        {
          provide: LOGGER_CONFIG,
          useValue: config
        }
      ]
    };
  }
  
  static forChild(): ModuleWithProviders<LoggerModule> {
    return {
      ngModule: LoggerModule,
      providers: []
    };
  }
}

// Utilisation dans AppModule
@NgModule({
  imports: [
    LoggerModule.forRoot({
      level: 'info',
      enableConsole: true,
      enableRemote: true,
      remoteUrl: 'https://api.example.com/logs'
    })
  ]
})
export class AppModule { }

// Utilisation dans Feature Module
@NgModule({
  imports: [
    LoggerModule.forChild()
  ]
})
export class FeatureModule { }
```

### Standalone Components (Angular 14+)

```typescript
// Standalone Component
@Component({
  selector: 'app-standalone',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <h1>Standalone Component</h1>
    <router-outlet></router-outlet>
  `
})
export class StandaloneComponent { }

// Bootstrap avec Standalone
import { bootstrapApplication } from '@angular/platform-browser';

bootstrapApplication(StandaloneComponent, {
  providers: [
    importProvidersFrom(HttpClientModule),
    provideRouter(routes),
    { provide: UserService, useClass: UserService }
  ]
});

// Lazy Loading de Standalone Components
const routes: Routes = [
  {
    path: 'standalone',
    loadComponent: () => import('./standalone.component').then(c => c.StandaloneComponent)
  }
];
```

---

## Références et Ressources

Cette cheat sheet complète couvre les aspects essentiels d'Angular pour les développeurs de tous niveaux. Pour approfondir :

1. **Documentation officielle Angular** - https://angular.io/docs
2. **Angular CLI Documentation** - https://angular.io/cli
3. **RxJS Documentation** - https://rxjs.dev/
4. **Angular Style Guide** - https://angular.io/guide/styleguide
5. **Angular DevKit** - Outils de développement
6. **Angular Material** - Composants UI
7. **Angular Universal** - Server-Side Rendering
8. **NgRx** - State Management
9. **Angular Testing Guide** - https://angular.io/guide/testing
10. **Angular Performance Checklist** - Optimisations

---

*Dernière mise à jour : Septembre 2025*