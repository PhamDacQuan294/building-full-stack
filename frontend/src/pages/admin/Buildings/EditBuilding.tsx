import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "sonner";
import BuildingForm from "./BuildingForm";
import { buildingService } from "@/services/admin/buildingService";
import type { BuildingFormData } from "@/types/admin/buildings";

export default function EditBuilding() {
  const { id } = useParams();
  const [districts, setDistricts] = useState<Record<string, string>>({});
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [initialValues, setInitialValues] = useState<BuildingFormData | undefined>(undefined);

  useEffect(() => {
  const fetchData = async () => {
    try {
      if (!id) return;

      setLoading(true);

      const [metaRes, detailRes] = await Promise.all([
        buildingService.getBuildingMeta(),
        buildingService.getBuildingDetail(id),
      ]);

      setDistricts(metaRes.data.districts || {});

      setInitialValues({
        name: detailRes.data.name ?? "",
        district: detailRes.data.district ?? "",
        ward: detailRes.data.ward ?? "",
        street: detailRes.data.street ?? "",
        basement: detailRes.data.numberOfBasement?.toString?.() ?? "",
        floorArea: detailRes.data.floorArea ?? "",
        rentPrice: detailRes.data.rentPrice ?? "",
        managerName: detailRes.data.managerName ?? "",
        managerPhone: detailRes.data.managerPhone ?? "",
        status: detailRes.data.status ?? "ACTIVE",
        imageUrl: detailRes.data.imageUrl ?? "",
        imageFile: null,
      });
    } catch (error) {
      console.error(error);
      toast.error("Không tải được dữ liệu toà nhà!");
    } finally {
      setLoading(false);
    }
  };

  fetchData();
}, [id]);

  useEffect(() => {
    const fetchDetail = async () => {
      try {
        if (!id) return;

        setLoading(true);
        const res = await buildingService.getBuildingDetail(id);

        setInitialValues(res.data);
      } catch (error) {
        console.error(error);
        toast.error("Không tải được chi tiết toà nhà!");
      } finally {
        setLoading(false);
      }
    };

    fetchDetail();
  }, [id]);

  const handleUpdate = async (values: BuildingFormData) => {
    try {
      if (!id) return;

      setLoading(true);

      let imageUrl = values.imageUrl || "";

      if (values.imageFile) {
        const uploadRes = await buildingService.uploadBuildingImage(
          values.imageFile,
        );
        imageUrl = uploadRes.data;
      }

      await buildingService.updateBuilding(id, {
        ...values,
        imageUrl,
      });

      toast.success("Cập nhật toà nhà thành công!");
      navigate("/admin/buildings");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật toà nhà thất bại!");
    } finally {
      setLoading(false);
    }
  };

  if (!initialValues && loading) {
    return <div>Đang tải dữ liệu...</div>;
  }

  if (!initialValues && !loading) {
    return <div>Không có dữ liệu</div>;
  }

  return (
    <div className="p-6 border rounded-xl border-border bg-background">
      <h1 className="mb-6 text-xl font-semibold">Cập nhật toà nhà</h1>
      <BuildingForm
        initialValues={initialValues}
        onSubmit={handleUpdate}
        loading={loading}
        submitText="Cập nhật"
        districts={districts}
      />
    </div>
  );
}
