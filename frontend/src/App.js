import React, { useEffect, useState } from "react";
import EditContactForm from "./EditContactForm";
import "./App.css";

function App() {
  const [contacts, setContacts] = useState([]);
  const [filteredContacts, setFilteredContacts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [editingContact, setEditingContact] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    phone: "",
    email: "",
    description: "",
  });
  const [errors, setErrors] = useState({});
  const [currentPage, setCurrentPage] = useState(1);
  const contactsPerPage = 5;

  useEffect(() => {
    fetchContacts();
  }, []);

  useEffect(() => {
    filterContacts();
  }, [searchTerm, contacts]);

  const fetchContacts = () => {
    fetch("/api/contacts")
      .then((res) => res.json())
      .then((data) => setContacts(data))
      .catch((err) => console.error(err));
  };

  const filterContacts = () => {
    const term = searchTerm.toLowerCase();
    const filtered = contacts.filter((c) =>
      Object.values(c).some((v) => v?.toString().toLowerCase().includes(term))
    );
    setFilteredContacts(filtered);
  };

  const validateForm = () => {
    const errs = {};
    const phoneRegex = /^\+?\d{10,15}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!formData.phone.match(phoneRegex)) errs.phone = "Невірний номер";
    if (!formData.email.match(emailRegex)) errs.email = "Невірний email";

    setErrors(errs);
    return Object.keys(errs).length === 0;
  };

  const handleAddContact = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    fetch("/api/contacts", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    })
      .then((res) => {
        if (res.status === 409) {
          return res.json().then((data) => {
            setErrors(data.errors);
          });
        }
        if (!res.ok) throw new Error("Помилка при збереженні");
        return res.json();
      })
      .then(() => {
        fetchContacts();
        setShowForm(false);
        setFormData({ firstName: "", lastName: "", phone: "", email: "", description: "" });
        setErrors({});
      })
      .catch((err) => console.error(err));
  };

  const handleDelete = (id) => {
    fetch(`/api/contacts/${id}`, { method: "DELETE" })
      .then(() => fetchContacts())
      .catch((err) => console.error(err));
  };

  const indexOfLast = currentPage * contactsPerPage;
  const indexOfFirst = indexOfLast - contactsPerPage;
  const currentContacts = filteredContacts.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(filteredContacts.length / contactsPerPage);

   function handleUpdate(updatedContact) {
    fetch(`/api/contacts/${updatedContact.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedContact),
    })
      .then((res) => res.json())
      .then((data) => {
        setContacts((prev) =>
          prev.map((c) => (c.id === data.id ? data : c))
        );
        setEditingContact(null);
      });
  }
  return (
    <div className="app-container">
      <h1>Контакти</h1>

      <div className="top-bar">
        <input
          type="text"
          placeholder="Пошук..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={() => setShowForm(true)}>Додати контакт</button>
      </div>

      {showForm && (
        <form className="contact-form" onSubmit={handleAddContact}>
          <input
            type="text"
            placeholder="Ім’я"
            value={formData.firstName}
            onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
          />
          <input
            type="text"
            placeholder="Прізвище"
            value={formData.lastName}
            onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
          />
          <input
            type="text"
            placeholder="Телефон"
            value={formData.phone}
            onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
            className={errors.phone ? "error" : ""}
          />
          {errors.phone && <div className="error-text">{errors.phone}</div>}

          <input
            type="text"
            placeholder="Email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            className={errors.email ? "error" : ""}
          />
          {errors.email && <div className="error-text">{errors.email}</div>}

          <textarea
            placeholder="Опис"
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
          ></textarea>

          <button type="submit">Зберегти</button>
          <button type="button" onClick={() => setShowForm(false)}>Скасувати</button>
        </form>
      )}

      <ul className="contact-list">
        {currentContacts.map((contact) => (
          <li key={contact.id}>
            <strong>{contact.firstName} {contact.lastName}</strong><br />
            📞 {contact.phone} <br />
            ✉️ {contact.email} <br />
            📝 {contact.description}
            <div className="actions">
              <button title="Редагувати"  onClick={() => setEditingContact(contact)}>✏️</button>
              <button title="Видалити" onClick={() => handleDelete(contact.id)}>🗑️</button>
            </div>
          </li>
        ))}
      </ul>

    {editingContact && (
  <EditContactForm
    contact={editingContact}
    onUpdate={handleUpdate}
    onCancel={() => setEditingContact(null)}
  />
)}



      {totalPages > 1 && (
        <div className="pagination">
          {[...Array(totalPages)].map((_, i) => (
            <button
              key={i}
              onClick={() => setCurrentPage(i + 1)}
              className={currentPage === i + 1 ? "active" : ""}
            >
              {i + 1}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}


export default App;
