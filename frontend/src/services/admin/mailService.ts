import api from "@/lib/axios";
import type {
  AssignmentMailPayload,
  EmailLogFilters,
  NewUserMailPayload,
  ResetPasswordMailPayload,
} from "@/types/admin/mail";

export const mailService = {
  sendNewUserMail: async (payload: NewUserMailPayload) => {
    const res = await api.post("/mails/new-user", payload);
    return res.data;
  },

  sendResetPasswordMail: async (payload: ResetPasswordMailPayload) => {
    const res = await api.post("/mails/reset-password", payload);
    return res.data;
  },

  sendAssignmentMail: async (payload: AssignmentMailPayload) => {
    const res = await api.post("/mails/assignment", payload);
    return res.data;
  },

  getLogs: async (params: EmailLogFilters) => {
    const res = await api.get("/mails/logs", { params });
    return res.data;
  },
};