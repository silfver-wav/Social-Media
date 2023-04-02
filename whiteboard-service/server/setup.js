const Sequelize = require('sequelize');

const sequelize = new Sequelize('imageDB', 'root', 'pass123', {
    host: 'mysql',
    port: 3306,
    dialect: 'mysql'
    });

const Image = sequelize.define('image', {
    name: {
        type: Sequelize.STRING,
        allowNull: false,
    },
    data: {
        type: Sequelize.TEXT,
        allowNull: false
    }
})
sequelize.sync().then(() => {
    console.log('Tables created successfully');
  })
  .catch((error) => {
    console.error('Error creating database:', error);
    process.exit(1);
  });

module.exports = {
Image: Image
};