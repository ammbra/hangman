module.exports = {
    getHomePage: (req, res) => {

    res.render("index.ejs", {
        title: "Welcome to Hangman! Let's have fun!",
    });
}
};
