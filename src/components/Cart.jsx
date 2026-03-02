import React, { useContext, useState, useEffect } from "react";
import AppContext from "../Context/Context";
import CheckoutPopup from "./CheckoutPopup";
import { Button } from "react-bootstrap";
import { toast } from "react-toastify";

const Cart = () => {
  const { cart, removeFromCart, clearCart } = useContext(AppContext);

  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    setCartItems(cart);
  }, [cart]);

  useEffect(() => {
    const total = cartItems.reduce(
      (acc, item) => acc + item.price * item.quantity,
      0
    );
    setTotalPrice(total);
  }, [cartItems]);

  const increaseQty = (id) => {
    const updated = cartItems.map((item) => {
      if (item.id === id) {
        if (item.quantity < item.stockQuantity) {
          return { ...item, quantity: item.quantity + 1 };
        } else {
          toast.info("Stock limit reached");
        }
      }
      return item;
    });
    setCartItems(updated);
  };

  const decreaseQty = (id) => {
    const updated = cartItems.map((item) =>
      item.id === id
        ? { ...item, quantity: Math.max(1, item.quantity - 1) }
        : item
    );
    setCartItems(updated);
  };

  const removeItem = (id) => {
    removeFromCart(id);
    setCartItems(cartItems.filter((i) => i.id !== id));
  };

  const imgUrl = (data, type = "image/jpeg") => {
    if (!data) return "/fallback-image.jpg";
    if (data.startsWith("http") || data.startsWith("data:")) return data;
    return `data:${type};base64,${data}`;
  };

  return (
    <div className="container mt-5 pt-4">
      <div className="card shadow">
        <div className="card-header bg-white">
          <h4>Shopping Cart</h4>
        </div>

        <div className="card-body">
          {cartItems.length === 0 ? (
            <h5 className="text-center">Cart is empty</h5>
          ) : (
            <>
              <table className="table align-middle">
                <thead>
                  <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Qty</th>
                    <th>Total</th>
                    <th></th>
                  </tr>
                </thead>

                <tbody>
                  {cartItems.map((item) => (
                    <tr key={item.id}>
                      <td className="d-flex align-items-center">
                        <img
                          src={imgUrl(item.imageData)}
                          width="70"
                          height="70"
                          className="me-3 rounded"
                        />
                        <div>
                          <strong>{item.name}</strong>
                          <br />
                          <small>{item.brand}</small>
                        </div>
                      </td>

                      <td>₹ {item.price}</td>

                      <td>
                        <div className="input-group" style={{ width: 120 }}>
                          <button
                            className="btn btn-outline-secondary"
                            onClick={() => decreaseQty(item.id)}
                          >
                            -
                          </button>

                          <input
                            className="form-control text-center"
                            value={item.quantity}
                            readOnly
                          />

                          <button
                            className="btn btn-outline-secondary"
                            onClick={() => increaseQty(item.id)}
                          >
                            +
                          </button>
                        </div>
                      </td>

                      <td>
                        ₹ {(item.price * item.quantity).toFixed(2)}
                      </td>

                      <td>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => removeItem(item.id)}
                        >
                          Remove
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <div className="d-flex justify-content-between mt-4">
                <h5>Total:</h5>
                <h5>₹ {totalPrice.toFixed(2)}</h5>
              </div>

              <div className="d-grid mt-3">
                <Button size="lg" onClick={() => setShowModal(true)}>
                  Proceed to Checkout
                </Button>
              </div>
            </>
          )}
        </div>
      </div>

      <CheckoutPopup
        show={showModal}
        handleClose={() => setShowModal(false)}
        cartItems={cartItems}
        totalPrice={totalPrice}
        clearCart={clearCart}
      />
    </div>
  );
};

export default Cart;