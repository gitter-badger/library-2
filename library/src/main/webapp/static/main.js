// function showMoreBooks(table, num) {
//
// }
//
// function createTableRow(cells) {
//     var tds = cells.map(function (cellContent) {
//         return '<td>' + cellContent + '</td>';
//     }).join('');
//     return '<tr>' + tds + '</tr>';
// }
//
// var countriesAndCitiesRow = function (ignore, index) {
//     return createTableRow([
//         data.Countries[index],
//         data.Cities[index]
//     ]);
// };

// $('#location').append(
//     $.map(data.Countries, countriesAndCitiesRow)
// );

fuction requestMoreBooks(num) {
    $.ajax({
        url: "ajax/more",
        type: "GET",
        dataType: "json"
    }).done(
        function(data) {

            console.log(JSON.stringify(data));
        });
}




// $('#location').append(
//     $.map(data.Countries, function (ignore, index) {
//         return '<tr><td>' + data.Countries[index] + '</td><td>' + data.Cities[index] + '</td></tr>';
//     }).join()
// );
