import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { Login } from "./pages/Login";
import { Dashboard } from "./pages/Dashboard";
import { SignUp } from "./pages/SignUp";
import { ProjectPage } from "./pages/ProjectPage";

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
  },
  {
    path: "/project/:id",
    element: <ProjectPage />
  },
]);

export function Routes() {
  return <RouterProvider router={router} />
}