import apiClient from "@/lib/axiosClient";
import type {
  ClientContactRequestPayload,
  ClientContactRequestResponse,
} from "@/types/client/contactRequest";

export const contactRequestService = {
  create: async (
    payload: ClientContactRequestPayload
  ): Promise<ClientContactRequestResponse> => {
    const res = await apiClient.post("/client/contact-requests", payload);
    return res.data.data;
  },
};