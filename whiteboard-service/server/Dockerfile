FROM node:16

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY ./package*.json ./

RUN npm install

# Bundle app source
COPY . .

# Expose server port
EXPOSE 5001

# Start the server
CMD [ "node", "server.js" ]