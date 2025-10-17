
// ========== CRUD OPERATIONS ==========

// Send form data (Create / Update)
function sendData() {
    const rowIndex = $("#rowIndex").val();
    const param1 = $("#param1").val();
    const param2 = $("#param2").val();
    const param3 = $("#param3").val();
    const param4 = $("#param4").val();
    const componentPath = $(".your-component").attr("data-path");

    $.ajax({
        url: '/bin/myServlet',
        type: 'POST',
        data: {
            rowIndex: rowIndex,
            param1: param1,
            param2: param2,
            param3: param3,
            param4: param4,
            componentPath: componentPath
        },
        success: function (response) {
            console.log('POST Response:', response);
            alert(response.message || 'Data saved successfully');
            fetchTableData();
            clearForm();
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// Fetch stored data (Read)
function fetchTableData() {
    const componentPath = $(".your-component").attr("data-path");

    $.ajax({
        url: '/bin/myServlet',
        type: 'GET',
        data: { path: componentPath },
        success: function (response) {
            console.log('GET Response:', response);
            if (response.data) {
                createTable(response.data);
            } else {
                console.error('No data found.');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// Create dynamic table
function createTable(data) {
    const tableContainer = $('#formDataTable');
    tableContainer.empty();

    const table = $('<table></table>');
    const headerRow = $('<tr></tr>');

    const headers = ['Name', 'Email', 'Subject', 'Message', 'Edit', 'Delete'];
    headers.forEach(header => headerRow.append(`<th>${header}</th>`));
    table.append(headerRow);

    data.forEach((item, index) => {
        const row = $('<tr></tr>');

        row.append(`<td>${item.name}</td>`);
        row.append(`<td>${item.email}</td>`);
        row.append(`<td>${item.subject}</td>`);
        row.append(`<td>${item.message}</td>`);

        const editCell = $('<td class="edit">Edit</td>').click(() => editRow(item, index));
        const deleteCell = $('<td class="delete">Delete</td>').click(() => deleteRow(index));

        row.append(editCell, deleteCell);
        table.append(row);
    });

    tableContainer.append(table);
}

// Edit row (fill form for update)
function editRow(item, index) {
    $("#rowIndex").val(index);
    $("#param1").val(item.name);
    $("#param2").val(item.email);
    $("#param3").val(item.subject);
    $("#param4").val(item.message);
}

// Delete specific row
function deleteRow(index) {
    const componentPath = $(".your-component").attr("data-path");

    $.ajax({
        url: '/bin/myServlet',
        type: 'POST',
        data: {
            rowIndex: index,
            delete: 'true',
            componentPath: componentPath
        },
        success: function (response) {
            console.log('DELETE Response:', response);
            alert('Row deleted successfully');
            fetchTableData();
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// Clear form inputs
function clearForm() {
    $("#rowIndex").val('');
    $("#param1").val('');
    $("#param2").val('');
    $("#param3").val('');
    $("#param4").val('');
}

// Fetch data on page load
$(document).ready(function () {
    fetchTableData();
});
