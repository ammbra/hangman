$(document).ready( function () {

    alreadyLoadedTable = false;
    currentUserName = 'none';
    currentGame = '';
    currentURL = "http://"+HOST+"/api"


    $('#login').click(function(e){
        e.preventDefault();

        currentUserName = $('#username').val();
        typeOfUser = $('#type').val();
        apiURL = (typeOfUser === 'ADMINISTRATOR') ? (currentURL+'/admin') : (currentURL+'/login/' + currentUserName)
        console.log(apiURL);

        $.ajax({
            url: apiURL,
            headers: {"Access-Control-Allow-Headers": "*", "Access-Control-Allow-Methods": "GET,OPTIONS", "Access-Control-Allow-Origin": apiURL, "Access-Control-Allow-Credentials": "true" },
            type: 'GET',
            dataType: 'text',
            crossDomain: true,
            timeout: 30000,
            success: function(data) {
                var title = 'Welcome to Hangman, ' + currentUserName
                $('#loginForm').hide();
                $('#welcome h3').text(title);
                $('#welcome, #logoutForm').show();
                if(typeOfUser === 'ADMINISTRATOR'){
                    $('#adminView').show();
                    updateAdminTable();
                } else if(typeOfUser === 'PLAYER') {
                    $('#adminView').hide();
                    $('#startGame').show();
                }
            }
        });
    });

    $(document).on('click', '.characterToPlay', function () {
        var characterChosen = $(this).val();
        $.ajax({
            url: currentURL + '/game/update/' + currentUserName + '/' + characterChosen,
            crossDomain: true,
            type: 'GET',
            dataType: 'json',
            timeout: 30000,
            success: function(data) {
                currentGame = data;
                loadCurrentGame();
            }
        });
    });

    $('#startGame').click(function(e){
        e.preventDefault();

        $('#secretWord').hide();
        $.ajax({
            url: currentURL + '/game/' + currentUserName,
            crossDomain: true,
            type: 'GET',
            dataType: 'json',
            timeout: 30000,
            success: function(data) {
                currentGame = data;
                $('#gameView').show();
                loadCurrentGame();
            }
        });
    });

    $('#logout').click(function(e){
        e.preventDefault();

        $('#loginForm').show();
        $('#logoutForm, #adminView, #welcome, #startGame, #gameView').hide();

    });
});

function loadCurrentGame() {
    var visibleWord = currentGame.visibleWord,
        attemptsLeft = currentGame.attemptsLeft,
        availableCharacters = currentGame.availableCharacters,
        status = currentGame.status,
        secretWord = currentGame.secretWord;

    $('#visibleWord').empty();
    for ( var i = 0; i < visibleWord.length; i++ ) {
        var letter = visibleWord.charAt(i),
            word = '<input class=\"character\" disabled=\"true\" type=\"button\" name=\"' + letter + '\" id=\"' + letter + '\" value=\"' + letter + '\">';
        $('#visibleWord').append(word);
    }
    $('#status').text(status);
    $('#attemptsLeft').text(attemptsLeft);
    $('#availableCharacters').removeClass('alert alert-warning');
    $('#availableCharacters').empty();

    if(status === 'ACTIVE') {
        printOutAvailableCharacters(availableCharacters);
    } else {
        $('#availableCharacters').addClass('alert alert-warning');
        $('#availableCharacters').text("You cannot keep playing. Please, click on Start/Continue Game.");
        if(status === 'LOST') {
            $('#secretWord').show();
            $('#secretWord').text("The secret word was " + secretWord);
        }
    }
}

function printOutAvailableCharacters(availableCharacters) {
    $(availableCharacters).each(function () {
        var letter = $(this)[0],
            button = '<input class=\"characterToPlay\" type=\"button\" name=\"' + letter + '\" id=\"' + letter + '\" value=\"' + letter + '\">';
        $('#availableCharacters').append(button);
    });
}

function updateAdminTable(){
    console.log(alreadyLoadedTable);
    if(alreadyLoadedTable === false) {
        var table = $('#playersTable').DataTable({
            "sAjaxSource": currentURL+"/admin/",
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "username"},
                {"mData": "secretWord"},
                {"mData": "visibleWord"},
                {"mData": "attemptsLeft"},
                {"mData": "status"},
                {"mData": "startDate"},
                {"mData": "endDate"}
            ]
        });
    }
    alreadyLoadedTable = true;
}