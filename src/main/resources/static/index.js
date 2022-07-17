const baseURI = 'https://api4leetcode.herokuapp.com/api';

function getLinkBasedOnDifficulty() {
    const form = document.forms.myForm;
    const checked = form.querySelector('input[name=flexRadioDefault]:checked');
    const difficulty = checked.value;
    const link = baseURI + '/random-question/' + difficulty;
    document.getElementById('randomProblemLink').href = link;
    return link;
}

async function getDailyChallenge() {
    try {
            const response = await fetch(baseURI + '/daily-challenge');

            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            let dailyChal = document.getElementById('daily-challenge-html');
            dailyChal.innerHTML = "";

            const result = await response.json();
            const resultStringify = JSON.stringify(result, null, 2);
            let h6 = document.createElement('h6');
            h6.append(resultStringify);

            dailyChal.append(h6);

            return result;
        } catch (err) {
            console.log(err);
        }
}

async function getRandomQuestion() {

    const linkBasedOnDifficulty = getLinkBasedOnDifficulty();

    try {
            const response = await fetch(linkBasedOnDifficulty);

            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            let randomQuest = document.getElementById('random-question-html');
            randomQuest.innerHTML = "";

            const result = await response.json();
            const resultStringify = JSON.stringify(result, null, 2);
            let h6 = document.createElement('h6');
            h6.append(resultStringify);

            randomQuest.append(h6);

            return result;
        } catch (err) {
            console.log(err);
        }
}