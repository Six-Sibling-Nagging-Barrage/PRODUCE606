FROM node:16.20.1
WORKDIR /frontend/jansori
COPY package.json package-lock.json ./
RUN npm install
COPY / /jansori
RUN npm run build
CMD ["npm", "run", "start"]
EXPOSE 3000