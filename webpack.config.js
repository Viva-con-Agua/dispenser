var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public/javascripts/');
var APP_DIR = path.resolve(__dirname, 'app/views/dispenser/');

var config = {
  entry: {
		javascript: APP_DIR + '/dispenser.js',
	},
  output: {
    path: BUILD_DIR,
    filename: 'dispenser.js',
		libraryTarget: 'var',
    library: 'EntryPoint'
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
