var request = require('request');

module.exports = {
    getHomePage: (req, res) => {

    res.render("index.ejs", {
        title: "Welcome to Hangman",
    });
}
};
