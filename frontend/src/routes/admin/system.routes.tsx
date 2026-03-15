import DashboardPage from "@/pages/admin/Dashboard";
import ProfilePage from "@/pages/admin/Profile";
import ActivityLogListPage from "@/pages/admin/ActivityLogs";
import MailPage from "@/pages/admin/Mails";
import EmailLogListPage from "@/pages/admin/EmailLogs";

const systemRoutes = [
  {
    index: true,
    element: <DashboardPage />,
  },
  {
    path: "dashboard",
    element: <DashboardPage />,
  },
  {
    path: "profile",
    element: <ProfilePage />,
  },
  {
    path: "activity-logs",
    element: <ActivityLogListPage />,
  },
  {
    path: "mails",
    element: <MailPage />,
  },
  {
    path: "email-logs",
    element: <EmailLogListPage />,
  },
];

export default systemRoutes;