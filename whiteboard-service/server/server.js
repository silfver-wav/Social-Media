var app = require('express')();
var http = require('http').createServer(app);
var io = require('socket.io')(http);

const setup = require('./setup');
const Image = setup.Image;

let counter = 0;
let canvasData;

io.on('connection', (socket) => {
    console.log('User Online');

    socket.emit('canvas-data', canvasData);

    socket.on("clear-canvas", () => {
        // clear the server's canvas state
        canvasData = [];
        io.emit('clear-canvas', canvasData)
      });

    socket.on('canvas-data', (data) => {
        canvasData = data;
        socket.broadcast.emit('canvas-data', data);
    })
    socket.on('save-image', (dataURL) => {
        // generate a unique file name
        const fileName = `whiteboard-${Date.now()}-${counter}.png`;
        counter++;
      
        // create a new Image instance
        const image = Image.build({
          name: fileName,
          data: dataURL,
        });
      
        // save the image to the database
        image.save().then(() => {
          console.log(`Saved image ${fileName} to the database`);
        }).catch((error) => {
          console.error(error);
        });
      });

    socket.on('request-image-list', async () => {
        // get the list of available images from the filesystem
        const imageList = await getImageList();
        // send the list of images back to the client
        console.log("This is the imageList sent back to the client");
        console.log(imageList);
        io.emit('image-list', imageList);
      })
      socket.on('request-image', async (selectedImage) => {
        try {
          // read the selected image from the filesystem
          const imageData = await readImage(selectedImage);
          const base64ImageData = imageData.toString('base64');
          
          // send the image data back to the client
          io.emit('image-data', base64ImageData);
        } catch (error) {
          console.error(error);
        }
      });  
})

  
async function readImage(imageName) {
  try {
    const image = await Image.findOne({
      where: {
      name: imageName
      }});
      return image.data;
    } catch (error) {
      console.error(error);
    }
}

async function getImageList() {
  try {
    const imageNames = await Image.findAll({
      attributes: ['name']
    });
    return imageNames.map(image => image.name);
  } catch (error) {
    console.error(error);
  }
}

  
var server_port = process.env.YOUR_PORT || process.env.PORT || 5002;
http.listen(server_port, () => {
    console.log("Started on : " + server_port);
})