import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const firebaseConfig = {
  apiKey: "AIzaSyBZ_cNYLGG9mzG5nUrnSsCacq6QuEQo-cY",
  authDomain: "jansori-393804.firebaseapp.com",
  projectId: "jansori-393804",
  storageBucket: "jansori-393804.appspot.com",
  messagingSenderId: "591770825704",
  appId: "1:591770825704:web:e3432cf2a2674a0cb32a5d",
};

function requestPermission() {
  console.log("Requesting permission...");
  Notification.requestPermission().then((permission) => {
    if (permission === "granted") {
      console.log("Notification permission granted.");

      const app = initializeApp(firebaseConfig);

      const messaging = getMessaging(app);
      getToken(messaging, {
        vapidKey:
          "BHi82Fs5R8eQ3iSvFi0JKucvS2_tlv0B7WHfexHpwgxpgldjPDIOW9w3VhxWl791QPTxj08dhCzq1CEggGul9mg",
      }).then((currentToken) => {
        if (currentToken) {
          console.log("토큰: " + currentToken);
        } else {
          console.log("토큰을 가져오지 못했습니다.");
        }
      });

      onMessage(messaging, (payload) => {
        console.log("Message received. ", payload);
        // ...
      });
    } else {
      console.log("권한이 없습니다.");
    }
  });
}

requestPermission();
