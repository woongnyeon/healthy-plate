import { useEffect, useMemo, useRef, useState } from "react";
import { validateProfileImage } from "../utills/fileValidators";

export const useProfileImage = () => {
  const fileRef = useRef<HTMLInputElement | null>(null);
  const [file, setFile] = useState<File | null>(null);

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

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const f = e.target.files?.[0];
    if (!f) return;

    const { ok, message } = validateProfileImage(f);
    if (!ok) {
      alert(message);
      e.target.value = "";
      return;
    }

    setFile(f);
  };

  const clear = () => {
    setFile(null);
    if (fileRef.current) fileRef.current.value = "";
  };

  return {
    fileRef,
    file,
    previewUrl,
    pick,
    onChange,
    clear,
  };
};
