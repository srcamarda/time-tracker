import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { Login } from "./pages/Login";
import { Dashboard } from "./pages/Dashboard";
import { SignUp } from "./pages/SignUp";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />,
  },
  {
    path: "/signup",
    element: <SignUp />
  },
  {
    path: "/dashboard",
    element: <Dashboard />
  }
]);

export function Routes() {
  return <RouterProvider router={router} />
}