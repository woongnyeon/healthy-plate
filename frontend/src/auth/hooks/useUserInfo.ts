import { useState } from "react";

interface DummyUser {
  id: string;
  name: string;
  email: string;
}

export const useUserInfo = () => {
  const [dummyUser, setDummyUserInfo] = useState<DummyUser>();

  const handleLogin = () => {
    setUserInfo();
  };

  const handleLogout = () => {
    clearUserInfo();
  };

  const setUserInfo = () => {
    const userInfo = {
      id: "dummy",
      name: "안성재명",
      email: "test@test.test",
    };

    setDummyUserInfo(userInfo);

    localStorage.setItem("user", JSON.stringify(userInfo));
  };

  const clearUserInfo = () => {
    localStorage.removeItem("user");

    setDummyUserInfo({
      id: "",
      email: "",
      name: "",
    });
  };

  return {
    dummyUser,
    handleLogin,
    handleLogout,
  };
};
