import React, { useState } from "react";
import "./EditContactForm.css";

function EditContactForm({ contact, onUpdate, onCancel }) {
  const [formData, setFormData] = useState(contact);
  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!formData.firstName?.trim()) {
      newErrors.firstName = "Ім’я обов’язкове";
    }

    if (!formData.lastName?.trim()) {
      newErrors.lastName = "Прізвище обов’язкове";
    }

    if (!formData.email?.trim()) {
      newErrors.email = "Email обов’язковий";
    } else if (!/^[\w.-]+@[\w.-]+\.\w{2,}$/.test(formData.email)) {
      newErrors.email = "Невірний формат email";
    }

    if (!formData.phone?.trim()) {
      newErrors.phone = "Телефон обов’язковий";
    } else if (!/^\+?\d{10,15}$/.test(formData.phone)) {
      newErrors.phone = "Невірний формат телефону";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      onUpdate(formData);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="edit-form">
      <label>
        Ім’я:
        <input
          type="text"
          name="firstName"
          value={formData.firstName || ""}
          onChange={handleChange}
        />
        {errors.firstName && <div className="error">{errors.firstName}</div>}
      </label>

      <label>
        Прізвище:
        <input
          type="text"
          name="lastName"
          value={formData.lastName || ""}
          onChange={handleChange}
        />
        {errors.lastName && <div className="error">{errors.lastName}</div>}
      </label>

      <label>
        Телефон:
        <input
          type="text"
          name="phone"
          value={formData.phone || ""}
          onChange={handleChange}
        />
        {errors.phone && <div className="error">{errors.phone}</div>}
      </label>

      <label>
        Email:
        <input
          type="text"
          name="email"
          value={formData.email || ""}
          onChange={handleChange}
        />
        {errors.email && <div className="error">{errors.email}</div>}
      </label>

      <label>
        Опис:
        <input
          type="text"
          name="description"
          value={formData.description || ""}
          onChange={handleChange}
        />
      </label>

      <div className="button-group">
        <button type="submit">Зберегти</button>
        <button type="button" onClick={onCancel}>Скасувати</button>
      </div>
    </form>
  );
}

export default EditContactForm;
