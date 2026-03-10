// src/layouts/LayoutDefault.jsx
import Header from "@/components/admin/partials/Header";
import Sidebar from "@/components/admin/partials/Sidebar";
import Footer from "@/components/admin/partials/Footer";
import { Outlet } from "react-router-dom";

export const LayoutDefault = () => {
  return (
    <div className="min-h-screen bg-gray-50">
      
      <Header/>

      <div className="flex min-h-[calc(100vh-56px)]">
        <Sidebar/>

        <main className="flex-1 min-w-0 p-6 bg-background">
          <Outlet/>
        </main>
      </div>

      <Footer />
    </div>
  );
};