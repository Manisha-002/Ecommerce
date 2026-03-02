import axios from "axios";
import React, { useState } from "react";
import { Modal, Button, Form, Toast, ToastContainer } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const CheckoutPopup = ({ show, handleClose, cartItems, totalPrice, clearCart }) => {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [validated, setValidated] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastVariant, setToastVariant] = useState("success");
  const [loading, setLoading] = useState(false);

  const submitOrder = async (e) => {
    e.preventDefault();
    const form = e.currentTarget;

    if (form.checkValidity() === false) {
      e.stopPropagation();
      setValidated(true);
      return;
    }

    setLoading(true);

    try {
      const order = {
        customerName: name,
        email: email,
        items: cartItems.map((i) => ({
          productId: i.id,
          quantity: i.quantity,
        })),
      };

      await axios.post(`${baseUrl}/api/orders/place`, order);

      setToastVariant("success");
      setToastMessage("Order placed successfully!");
      setShowToast(true);

      clearCart();

      setTimeout(() => {
        handleClose();
        navigate("/");
      }, 2000);

    } catch (err) {
      setToastVariant("danger");
      setToastMessage("Order failed. Try again.");
      setShowToast(true);
    } finally {
      setLoading(false);
    }
  };

  const imgUrl = (data, type = "image/jpeg") => {
    if (!data) return "/fallback-image.jpg";
    if (data.startsWith("http") || data.startsWith("data:")) return data;
    return `data:${type};base64,${data}`;
  };

  return (
    <>
      <Modal show={show} onHide={handleClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Checkout</Modal.Title>
        </Modal.Header>

        <Form noValidate validated={validated} onSubmit={submitOrder}>
          <Modal.Body>

            {cartItems.map((item) => (
              <div key={item.id} className="d-flex mb-3 border-bottom pb-2">
                <img
                  src={imgUrl(item.imageData)}
                  style={{ width: 70, height: 70 }}
                  className="rounded me-3"
                />

                <div>
                  <h6>{item.name}</h6>
                  <small>Qty: {item.quantity}</small>
                  <br />
                  <small>₹ {(item.price * item.quantity).toFixed(2)}</small>
                </div>
              </div>
            ))}

            <h5 className="text-center my-3">
              Total ₹{totalPrice.toFixed(2)}
            </h5>

            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                required
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control
                required
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>

          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>

            <Button type="submit" disabled={loading}>
              {loading ? "Processing..." : "Confirm Purchase"}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>

      <ToastContainer position="top-end" className="p-3">
        <Toast
          show={showToast}
          onClose={() => setShowToast(false)}
          delay={3000}
          autohide
          bg={toastVariant}
        >
          <Toast.Body className="text-white">{toastMessage}</Toast.Body>
        </Toast>
      </ToastContainer>
    </>
  );
};

export default CheckoutPopup;