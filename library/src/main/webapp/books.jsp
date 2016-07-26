<%@ page language="java" contentType="text/html; charset=Unicode"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=Unicode">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Библиотека</title>

    <%-- <link href="<c:url value="/static/style.css" />" rel="stylesheet" type="text/css"> --%>
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js" />"></script>
    <%-- <c:url var="cdn" value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js" />
    <script src="${cdn}"> </script> --%>
    <%-- <c:url var="js_url" value="/resources/main.js" />
    <script src="${js_url}"></script> --%>
  <style>
    table, th, td {
      border: 1px solid black;
      empty-cells: show;
    }
  </style>
  </head>
  <body>
    <table id="booksTable">
      <caption>Книги</caption>
      <tr>
        <th>ISBN</th>
        <th>Автор</th>
        <th>Название</th>
        <th>Текущий держатель</th>
        <th>Удалить</th>
      </tr>
    </table>

    <script>
    $(function () {
      var token = $("meta[name='_csrf']").attr("content");
      var header = $("meta[name='_csrf_header']").attr("content");
      $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
      });
    });

    var libraryUserID = 3;

    function moreBooks() {
        $.ajax({
            url: "ajax/moreBooks",
            type: "GET",
            dataType: "json"
        }).done(
            function(data) {
                data.forEach(function(book, i) {
                    var uniqueBorrowerID = "borrower" + book.isbn;
                    var uniqueDeleteID = "delete" + book.isbn;
                    $("#booksTable").append(
                        "<tr><td>" + book.isbn + "</td><td>" + book.author +
                        "</td><td>" + book.title + "</td><td id=" + uniqueBorrowerID + ">" +
                        borrowerNameOrNothing(book.borrower) + "</td><td id=" + uniqueDeleteID + "></tr>"
                    );
                    addButtonToBorrowerCell(book, uniqueBorrowerID);
                    addButtonToDeleteCell(book, uniqueDeleteID);
                })
            });
    }

    function borrowerNameOrNothing(borrower) {
        return borrower !== null && borrower.id != libraryUserID ? borrower.name : "";
    }

    function addButtonToBorrowerCell(book, cell) {
        if (book.borrower !== null) {
            if (book.borrower.id == libraryUserID) {
                var button = $("<button>Вернуть</button>");
                console.log("addButtonToBorrowerCell() :: вернуть :: uniqueID = " +
                    cell + "; isbn = " + book.isbn);
                button.click({ isbn: book.isbn }, returnBookHandler);
                button.appendTo("#" + cell);
            }
        } else {
            var button = $("<button>Взять</button>");
            button.click({ book_isbn: book.isbn}, borrowBookHandler);
            button.appendTo("#" + cell);
        }
    }

    function addButtonToDeleteCell(book, cell) {
        var button = $("<button>Удалить</button>");
        button.click(book.isbn, deleteBookHandler);
        button.appendTo("#" + cell);
    }

    /******************
     * Event handlers
     ******************/

    function returnBookHandler(event) {
        console.log("returnBookHandler() :: event.data.isbn = " + event.data.isbn);
        console.log("    :: JSON.stringify(event.data.isbn) = " + JSON.stringify(event.data.isbn));
        $.ajax({
            url: "ajax/returnBook",
            type: "POST",
            dataType: "json",
            data: event.data.isbn
        }).done(function() {
            console.log("returned!");
        });
    }

    function borrowBookHandler(event) {
        $.ajax({
            url: "ajax/borrowBook",
            type: "POST",
            dataType: "json",
            data: event.data.isbn
        }).done(function() {
            console.log("borrowed!");
        });
    }

    function deleteBookHandler(isbn) {
        $.post("ajax/deleteBook", JSON.stringify(isbn), function() {
            alert("deleted!");
        });
    }

    // function addBook() {
    //     $.ajax({
    //         url: "ajax/addBook",
    //         type: "GET",
    //         dataType: "json"
    //     }).done(
    //         function(data) {
    //             data.forEach(function(row, i) {
    //                 $("#booksTable").append(
    //                     "<tr><td>" + row.isbn + "</td><td>" + row.author +
    //                     "</td><td>" + row.title + "</td><td>" + row.borrower.name + "</td></tr>"
    //                 )
    //             })
    //         });
    // }

    $(document).ready(moreBooks)
    </script>

    <button id="append" type="button" onClick="moreBooks()">Показать еще 5 книг</button>

  </body>
</html>
