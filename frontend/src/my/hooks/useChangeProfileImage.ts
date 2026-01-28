import { useEffect, useMemo, useRef, useState } from "react";
import { usePreSignedUrlMutation } from "../hooks/useMyQuery";
import { validateProfileImage } from "../../auth/utills/fileValidators";

const MAX_FILE_SIZE = 5 * 1024 * 1024;

export const useChangeProfileImage = (options?: {
  onUploadSuccess?: (url: string) => void;
}) => {
  const fileRef = useRef<HTMLInputElement | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [uploadError, setUploadError] = useState<string | null>(null);
  const [uploadedUrl, setUploadedUrl] = useState<string | null>(null);

  const preSignedUrlMutation = usePreSignedUrlMutation();

  const previewUrl = useMemo(() => {
    if (!file) return null;
    return URL.createObjectURL(file);
  }, [file]);

  useEffect(() => {
    return () => {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl]);

  const pick = () => fileRef.current?.click();

  const upload = async (selcetedFile?: File): Promise<string | null> => {
    const fileToUpload = selcetedFile || file;
    if (!fileToUpload) {
      setUploadError("업로드할 파일이 없습니다.");
      return null;
    }

    if (fileToUpload.size > MAX_FILE_SIZE) {
      setUploadError("파일 크기는 5MB 이하여야 합니다.");
      return null;
    }

    setIsUploading(true);
    setUploadError(null);

    try {
      const { presignedUrl, fileUrl } = await preSignedUrlMutation.mutateAsync({
        contentType: fileToUpload.type,
        fileSize: fileToUpload.size,
      });

      const uploadResponse = await fetch(presignedUrl, {
        method: "PUT",
        headers: {
          "Content-Type": fileToUpload.type,
        },
        body: fileToUpload,
      });

      if (!uploadResponse.ok) {
        throw new Error(`S3 업로드 실패: ${uploadResponse.status}`);
      }
      setUploadedUrl(fileUrl);
      options?.onUploadSuccess?.(fileUrl);
      return fileUrl;
    } catch (error) {
      const errorMessage =
        error instanceof Error
          ? error.message
          : "업로드 중 오류가 발생했습니다.";
      setUploadError(errorMessage);
      return null;
    } finally {
      setIsUploading(false);
    }
  };

  const onChange = async (
    e: React.ChangeEvent<HTMLInputElement>,
    autoUpload = false,
  ) => {
    const f = e.target.files?.[0];
    if (!f) return;

    if (f.size > MAX_FILE_SIZE) {
      alert("파일 크기는 5MB 이하여야 합니다.");
      e.target.value = "";
      return;
    }

    const { ok, message } = validateProfileImage(f);
    if (!ok) {
      alert(message);
      e.target.value = "";
      return;
    }

    setFile(f);
    setUploadError(null);

    if (autoUpload) {
      await upload(f);
    }
  };

  const clear = () => {
    setFile(null);
    setUploadedUrl(null);
    setUploadError(null);
    if (fileRef.current) fileRef.current.value = "";
  };

  return {
    fileRef,
    file,
    previewUrl,
    pick,
    onChange,
    upload,
    clear,
    isUploading,
    uploadError,
    uploadedUrl,
  };
};
