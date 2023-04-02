FROM node:16

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY ./package*.json ./

RUN npm install

# Bundle app source
COPY . .

# Build the UI
RUN npm run build

# Expose UI server port
EXPOSE 3000

# Start the UI server
CMD [ "npm", "start" ]