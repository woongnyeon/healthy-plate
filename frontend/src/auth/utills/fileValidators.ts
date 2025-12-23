const ALLOWED_TYPES = ["image/jpeg", "image/png"];
const MAX_SIZE = 5 * 1024 * 1024;

export const validateProfileImage = (file: File) => {
  if (!ALLOWED_TYPES.includes(file.type)) {
    return { ok: false, message: "JPG, PNG 파일만 업로드 가능합니다." };
  }
  if (file.size > MAX_SIZE) {
    return { ok: false, message: "최대 5MB까지 업로드 가능합니다." };
  }
  return { ok: true, message: "" };
};
