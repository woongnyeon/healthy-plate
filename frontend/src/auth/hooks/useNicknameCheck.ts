import { useMemo, useState } from "react";
import { useAuthApi } from "./useAuthApi";
import { useMutation } from "@tanstack/react-query";

export const useNicknameCheck = (nickname: string) => {
  const { checkNickname } = useAuthApi();

  const [lastCheckedNickname, setLastCheckedNickname] = useState<string>("");
  const [isAvailable, setIsAvailable] = useState<boolean | null>(null);
  const [message, setMessage] = useState<string>("중복 확인을 진행해주세요.");

  const mutation = useMutation({
    mutationFn: (nick: string) => checkNickname(nick),
    onSuccess: (isDuplicated, nick) => {
      const available = !isDuplicated; // ✅ 서버 의미 반전

      setLastCheckedNickname(nick);
      setIsAvailable(available);
      setMessage(
        available ? "사용 가능한 닉네임입니다." : "이미 사용 중인 닉네임입니다."
      );
    },

    onError: () => {
      setLastCheckedNickname("");
      setIsAvailable(null);
      setMessage("중복 확인에 실패했습니다. 잠시 후 다시 시도해주세요.");
    },
  });

  const isCheckedForCurrent = useMemo(() => {
    return nickname.trim().length > 0 && nickname === lastCheckedNickname;
  }, [nickname, lastCheckedNickname]);

  const canSubmit = isCheckedForCurrent && isAvailable === true;

  const onCheck = () => {
    const newNickname = nickname.trim();
    if (!newNickname) {
      setMessage("닉네임을 입력해주세요");
      setIsAvailable(null);
      setLastCheckedNickname("");
      return;
    }
    mutation.mutate(newNickname);
  };

  return {
    checking: mutation.isPending,
    isAvailable,
    message,
    onCheck,
    canSubmit,
    isCheckedForCurrent,
    lastCheckedNickname,
  };
};
