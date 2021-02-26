module.exports = {
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/
    outputDir: 'target/dist',
    assetsDir: 'static',

    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:3009',
                ws: true,
                changeOrigin: true
            }
        }
    },
}