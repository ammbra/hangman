$(document).ready( function () {

    alreadyLoadedTable = false;
    currentUserName = 'none';
    currentGame = '';
    currentURL = "http://localhost:8080/api"


    $('#login').click(function(e){
        e.preventDefault();

        currentUserName = $('#username').val();
        // typeOfUser = $('#type').val();
        apiURL = currentURL+'/login/' + currentUserName;

        $.ajax({
            url: apiURL,
            type: 'GET',
            dataType: 'text',
            timeout: 30000,
            success: function(data) {
                var title = 'Welcome to Hangman, ' + currentUserName;
                $('#loginForm').hide();
                $('#welcome').text(title);
                $('#welcome, #logoutForm').show();
                $('#adminView').hide();
                $('#startGame').show();
            }
        });
    });

    $('#admin').click(function(e) {

        currentUserName = $('#username').val();
        apiURL = currentURL+'/admin';

        $.ajax({
            url: apiURL,
            type: 'GET',
            dataType: 'text',
            timeout: 30000,
            success: function(data) {
                $('#loginForm').hide();
                $('#welcome').text( 'Welcome to Hangman, Admin');
                $('#welcome, #logoutForm').show();
                $('#adminView').show();
                updateAdminTable(apiURL);
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
            word = '<input class=\"character\" type=\"button\" name=\"' + letter + '\" id=\"' + letter + '\" value=\"' + letter + '\">';
        $('#visibleWord').append(word);
    }
    $('#status').text(status);
    $('#attemptsLeft').text(attemptsLeft);
    $('#alert').removeClass('alert alert-warning');
    $('#alert').empty();
    $('#play').show();

    $('#availableCharacters').empty();

    if(status === 'ACTIVE') {
        printOutAvailableCharacters(availableCharacters);
    } else {
        $('#play').hide();
        $('#alert').addClass('alert alert-warning');
        $('#alert').text("You cannot keep playing. Please, click on Start/Continue Game.");
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

function updateAdminTable(apiURL){
    if(alreadyLoadedTable === false) {
        var table = $('#playersTable').DataTable({
            "sAjaxSource": apiURL,
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "username"},
                {"mData": "secretWord"},
                {"mData": "attemptsLeft"},
                {"mData": "status"},
                {"mData": "startDate"},
                {"mData": "endDate"}
            ]
        });
    }
    alreadyLoadedTable = true;
}