// src/components/admin/Sidebar.jsx
import { NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside className="border-r w-50 shrink-0 bg-muted text-sidebar-foreground border-border">
      <div className="inner-menu">
        <ul className="p-0 m-0 list-none">
          
          <li>
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                `block border-b border-sidebar-border px-[10px] py-[6px] transition 
                ${
                  isActive
                    ? "bg-sidebar-accent text-sidebar-accent-foreground font-medium"
                    : "hover:bg-sidebar-accent/60"
                }`
              }
            >
              Tổng quan
            </NavLink>
          </li>

          <li>
            <NavLink
              to="/admin/buildings"
              className={({ isActive }) =>
                `block border-b border-sidebar-border px-[10px] py-[6px] transition 
                ${
                  isActive
                    ? "bg-sidebar-accent text-sidebar-accent-foreground font-medium"
                    : "hover:bg-sidebar-accent/60"
                }`
              }
            >
              Danh sách toà nhà
            </NavLink>
          </li>

          <li>
            <NavLink
              to="/products-category"
              className={({ isActive }) =>
                `block border-b border-sidebar-border px-[10px] py-[6px] transition 
                ${
                  isActive
                    ? "bg-sidebar-accent text-sidebar-accent-foreground font-medium"
                    : "hover:bg-sidebar-accent/60"
                }`
              }
            >
              Danh mục sản phẩm
            </NavLink>
          </li>

        </ul>
      </div>
    </aside>
  );
};

export default Sidebar;