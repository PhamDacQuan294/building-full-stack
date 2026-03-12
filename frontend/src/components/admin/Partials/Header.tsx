// src/components/admin/Header.jsx
import { removeToken } from "@/utils/auth";
import { Link } from "react-router-dom";

const Header = () => {
  const handleLogout = () => {
      removeToken();
      window.location.href = "/admin/login";
  };

  return (
    <header className="top-0 z-[999] w-full border-b border-border bg-gradient-glass backdrop-blur-xl shadow-soft">
      <div className="px-6">
        <div className="flex items-center h-14">
          
          {/* Left */}
          <div className="w-3/12">
            <Link
              to="/admin/dashboard"
              className="text-xl font-extrabold tracking-wide text-primary"
            >
              ADMIN
            </Link>
          </div>

          {/* Right */}
          <div className="w-9/12 text-right">
            <Link
              to="/admin/my-account"
              className="mr-2 inline-flex items-center rounded-md bg-primary px-4 py-1.5 text-sm font-medium text-primary-foreground shadow-sm transition hover:opacity-90"
            >
              My account
            </Link>

            <Link
              to="/admin/auth/logout"
              onClick={handleLogout}
              className="inline-flex items-center rounded-md bg-destructive px-4 py-1.5 text-sm font-medium text-destructive-foreground shadow-sm transition hover:opacity-90"
            >
              Đăng xuất
            </Link>
          </div>

        </div>
      </div>
    </header>
  );
};

export default Header;