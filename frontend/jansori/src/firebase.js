import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREABSE_APIKEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTHDOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECTID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGEBUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGINGSENDERID,
  appId: process.env.REACT_APP_FIREBASE_APPID,
};

function requestPermission() {
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {

      const app = initializeApp(firebaseConfig);

      const messaging = getMessaging(app);
      getToken(messaging, {
        vapidKey: process.env.REACT_APP_FIREBASE_VAPIDKEY,
      }).then((currentToken) => {
        if (currentToken) {
          localStorage.setItem('fcmToken', currentToken);
        } else {
          console.log('토큰을 가져오지 못했습니다.');
        }
      });

      onMessage(messaging, (payload) => {
        console.log('Message received. ', payload);
        // ...
      });
    } else {
      console.log('권한이 없습니다.');
    }
  });
}

requestPermission();
