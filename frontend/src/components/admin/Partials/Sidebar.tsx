import { NavLink } from "react-router-dom";
import { removeToken } from "@/utils/auth";

const Sidebar = () => {

  const handleLogout = () => {
    removeToken();
    window.location.href = "/login";
  };

  const linkClass = ({ isActive }: { isActive: boolean }) =>
    `block border-b border-sidebar-border px-[10px] py-[6px] transition
    ${
      isActive
        ? "bg-sidebar-accent text-sidebar-accent-foreground font-medium"
        : "hover:bg-sidebar-accent/60"
    }`;

  return (
    <aside className="border-r w-50 shrink-0 bg-muted text-sidebar-foreground border-border">
      <div className="inner-menu">
        <ul className="p-0 m-0 list-none">

          <li>
            <NavLink to="/dashboard" className={linkClass}>
              Tổng quan
            </NavLink>
          </li>

          <li>
            <NavLink to="/admin/buildings" className={linkClass}>
              Danh sách toà nhà
            </NavLink>
          </li>

          <li>
            <NavLink to="/admin/roles" className={linkClass}>
              Danh sách nhóm quyền
            </NavLink>
          </li>

          <li>
            <button
              onClick={handleLogout}
              className="w-full text-left px-[10px] py-[6px] hover:bg-red-500 hover:text-white"
            >
              Đăng xuất
            </button>
          </li>

        </ul>
      </div>
    </aside>
  );
};

export default Sidebar;