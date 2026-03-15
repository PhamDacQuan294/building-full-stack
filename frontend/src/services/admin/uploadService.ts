import api from "@/lib/axios";

export const uploadService = {
  uploadImage: async (file: File) => {
    const formData = new FormData();
    formData.append("file", file);

    const res = await api.post("/upload/image", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return res.data;
  },
};