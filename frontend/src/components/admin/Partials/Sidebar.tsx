import { NavLink } from "react-router-dom";

const Sidebar = () => {
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
            <NavLink to="/admin/users" className={linkClass}>
              Danh sách người dùng
            </NavLink>
          </li>

          <li>
            <NavLink to="/admin/customers">Danh sách khách hàng</NavLink>
          </li>

          <li>
            <NavLink to="/admin/transactions">Danh sách giao dịch</NavLink>
          </li>
        </ul>
      </div>
    </aside>
  );
};

export default Sidebar;
