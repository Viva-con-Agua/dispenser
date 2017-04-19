var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public/javascripts/');
var APP_DIR = path.resolve(__dirname, 'app/views/admin/');

var config = {
  entry: {
		javascript: APP_DIR + '/admin-workspace.jsx',
	},
  output: {
    path: BUILD_DIR,
    filename: 'admin.js'
  },
	module: {
    rules : [
      {
        test : /\.jsx?/,
				exclude: /node_modules/,
				query:{
					presets: ['es2015', 'react']
				},
        include : APP_DIR,
        loader : 'babel-loader'
      },
			{
	    	test: /\.html$/,
  	  	loader: "file-loader?name=[name].[ext]",
  		}
    ]
  },

};

module.exports = config;
