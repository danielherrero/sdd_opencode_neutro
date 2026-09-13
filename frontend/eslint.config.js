const angular = require('angular-eslint');

module.exports = angular.configs.tsRecommended.map(config => ({ ...config, files: ['**/*.ts'] }));
