import React from "react";
import Switch from "react-switch";

const Toggle = ({ isPublic, onToggle }) => {
  const label = isPublic ? "공개" : "비공개";
  const Color = "#B197FC";
  return (
    <div style={{ display: "flex", alignItems: "center" }}>
      <Switch
        onChange={onToggle}
        checked={isPublic}
        onColor={Color}
        onHandleColor="#ffffff"
        offColor="#dddddd"
        offHandleColor="#ffffff"
      />{" "}
      <label style={{ marginLeft: "10px" }}>{label}</label>
    </div>
  );
};

export default Toggle;
