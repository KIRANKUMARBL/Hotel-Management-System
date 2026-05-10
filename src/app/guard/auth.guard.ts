import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route) => {

  const router = inject(Router);
  const token = sessionStorage.getItem('token');

  if (!token) {
    router.navigate(['/login']);
    return false;
  }

  const payload = JSON.parse(atob(token.split('.')[1]));

  // ✅ IMPORTANT FIX
  const userRole = payload.role?.replace('ROLE_', '');

  const allowedRoles = route.data?.['roles'];

  console.log("User Role:", userRole);
  console.log("Allowed Roles:", allowedRoles);

  if (allowedRoles && !allowedRoles.includes(userRole)) {
    alert("Access Denied");
    router.navigate(['/dashboard']);
    return false;
  }

  return true;
};